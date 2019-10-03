package org.isisaddons.module.publishmq.dom.mq.spi;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.iactn.Interaction;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.utils.InteractionDtoUtils;
import org.isisaddons.module.publishmq.dom.servicespi.InteractionExecutionRepository;
import org.isisaddons.module.publishmq.dom.servicespi.PublisherServiceUsingActiveMq;
import org.isisaddons.module.publishmq.dom.servicespi.PublisherServiceUsingActiveMqStatusProvider;

@DomainService(
        nature = NatureOfService.DOMAIN,
        menuOrder = "" + Integer.MAX_VALUE // after the JDO publishers
)
public class InteractionExecutionRepositoryMq implements InteractionExecutionRepository {

    @Inject
    PublisherServiceUsingActiveMqStatusProvider statusProvider;

    private static final Logger LOG = LoggerFactory.getLogger(InteractionExecutionRepositoryMq.class);

    //region > keys
    public static final String ROOT = PublisherServiceUsingActiveMq.ROOT;

    public static final String KEY_VM_TRANSPORT_URL = ROOT + "vmTransportUri";
    public static final String KEY_VM_TRANSPORT_URL_DEFAULT = "vm://broker";

    public static final String KEY_MEMBER_INTERACTIONS_QUEUE = ROOT + "memberInteractionsQueue";
    public static final String KEY_MEMBER_INTERACTIONS_QUEUE_DEFAULT = "memberInteractionsQueue";

    public static final String KEY_PROPAGATE_EXCEPTION = ROOT + "propagateException";
    public static final String KEY_PROPAGATE_EXCEPTION_DEFAULT = "false";

    //endregion



    //region > fields

    private ConnectionFactory jmsConnectionFactory;
    private Connection jmsConnection;

    private static boolean transacted = true;

    private String vmTransportUrl;
    String memberInteractionsQueueName;

    private boolean enabled;

    private boolean propagateException;

    //endregion

    //region > init, shutdown

    @PostConstruct
    public void init(Map<String,String> properties) {

        enabled = statusProvider.isEnabled();
        if(!enabled) {
            LOG.warn("JMS publishing NOT enabled");
            return;
        }

        vmTransportUrl = properties.getOrDefault(KEY_VM_TRANSPORT_URL, KEY_VM_TRANSPORT_URL_DEFAULT);
        memberInteractionsQueueName = properties.getOrDefault(KEY_MEMBER_INTERACTIONS_QUEUE,
                KEY_MEMBER_INTERACTIONS_QUEUE_DEFAULT);

        memberInteractionsQueueName = properties.getOrDefault(KEY_MEMBER_INTERACTIONS_QUEUE,
                KEY_MEMBER_INTERACTIONS_QUEUE_DEFAULT);

        propagateException = properties.getOrDefault(KEY_PROPAGATE_EXCEPTION, KEY_PROPAGATE_EXCEPTION_DEFAULT).equalsIgnoreCase("true");

        connect();

    }

    void connect() {
        jmsConnectionFactory = new ActiveMQConnectionFactory(vmTransportUrl);

        try {
            jmsConnection = jmsConnectionFactory.createConnection();
        } catch (JMSException e) {
            LOG.error("Unable to create connection", e);
        }

        if(jmsConnection != null) {
            try {
                jmsConnection.start();
            } catch (JMSException e) {
                LOG.error("Unable to start connection", e);
                closeSafely(jmsConnection);
                jmsConnection = null;
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closeSafely(jmsConnection);
    }


    private static void closeSafely(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                //ignore
            }
        }
    }

    private static void closeSafely(Session session) {
        try {
            session.close();
        } catch (JMSException e) {
            // ignore
        }
    }

    private static void stopSafely(final BrokerService broker) {
        if(broker==null) {
            return;
        }
        try {
            broker.stop();
        } catch (Exception ignore) {
        }
    }

    //endregion


    //region > publish (execution)

    @Override
    @Programmatic
    public void persist(final Interaction.Execution<?, ?> execution) {

        if(jmsConnection == null) {
            LOG.warn("No JMS connection; interaction will not be published");
            return;
        }

        final InteractionDto interactionDto = InteractionDtoUtils.newInteractionDto(execution);

        sendUsingJms(interactionDto);
    }

    private void sendUsingJms(final InteractionDto interactionDto) {
        final String xml = InteractionDtoUtils.toXml(interactionDto);

        Session session = null;
        try {

            session = jmsConnection.createSession(transacted, Session.SESSION_TRANSACTED);
            TextMessage message = session.createTextMessage(xml);

            final String transactionId = interactionDto.getTransactionId();
            final int sequence = interactionDto.getExecution().getSequence();
            final String executionId = transactionId + "." + sequence;
            final String memberIdentifier = interactionDto.getExecution().getMemberIdentifier();

            message.setJMSMessageID(executionId);
            message.setJMSType(memberIdentifier);

            if(LOG.isInfoEnabled()) {
                LOG.info(String.format(
                        "Sending JMS message, id:%s; type:%s",
                        message.getJMSMessageID(), message.getJMSType()));
            }

            final Queue queue = session.createQueue(memberInteractionsQueueName);
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(message);

            session.commit();

        } catch (JMSException ex) {
            rollback(session);
            if(propagateException) {
                throw new ApplicationException(String.format(
                        "Failed to publish message, and aborting (as per '%s' property)", KEY_PROPAGATE_EXCEPTION),
                        ex);
            } else {
                LOG.error(String.format(
                        "Failed to publish message, but continuing (as per '%s' property)", KEY_PROPAGATE_EXCEPTION),
                        ex);
            }
        } finally {
            if(session != null) {
                closeSafely(session);
            }
        }
    }


    private static void rollback(final Session session) {
        try {
            if (session != null) {
                session.rollback();
            }
        } catch (JMSException ex) {
            // ignore
        }
    }
    //endregion


    //region > republish
    /**
     * Private API.
     * @param interactionDto
     */
    @Programmatic
    public void republish(final InteractionDto interactionDto) {
        sendUsingJms(interactionDto);
    }

    //endregion


}
