package org.isisaddons.module.publishmq.dom.servicespi;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
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
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.publish.EventMetadata;
import org.apache.isis.applib.services.publish.EventPayload;
import org.apache.isis.applib.services.publish.EventPayloadForActionInvocation;
import org.apache.isis.applib.services.publish.EventPayloadForObjectChanged;
import org.apache.isis.applib.services.publish.EventSerializer;
import org.apache.isis.applib.services.publish.PublishingService;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.ActionInvocationMementoDto;
import org.isisaddons.module.publishmq.dom.ActionInvocationMementoUtils;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishingServiceUsingMqEmbedded implements PublishingService {

    private final static Logger LOG = LoggerFactory.getLogger(PublishingServiceUsingMqEmbedded.class);

    private static final String ROOT = PublishMqModule.class.getPackage().getName() + ".";

    public static final String KEY_VM_TRANSPORT_URL = "isis.services." + PublishingServiceUsingMqEmbedded.class.getSimpleName() + ".vmTransportUri";
    public static final String KEY_VM_TRANSPORT_URL_DEFAULT = "vm://broker";

    public static final String KEY_ACTION_INVOCATIONS_QUEUE = "isis.services." + PublishingServiceUsingMqEmbedded.class.getSimpleName() + ".actionInvocationsQueue";
    public static final String KEY_ACTION_INVOCATIONS_QUEUE_DEFAULT = "actionInvocationsQueue";

    private ConnectionFactory jmsConnectionFactory;
    private Connection jmsConnection;

    private static boolean transacted = true;

    private String vmTransportUrl;
    String actionInvocationsQueueName;


    @PostConstruct
    public void init(Map<String,String> properties) {

        vmTransportUrl = properties.getOrDefault(KEY_VM_TRANSPORT_URL, KEY_VM_TRANSPORT_URL_DEFAULT);
        actionInvocationsQueueName = properties.getOrDefault(KEY_ACTION_INVOCATIONS_QUEUE, KEY_ACTION_INVOCATIONS_QUEUE_DEFAULT);

        jmsConnectionFactory = new ActiveMQConnectionFactory(vmTransportUrl);

        try {
            jmsConnection = jmsConnectionFactory.createConnection();
        } catch (JMSException e) {
            LOG.error("Unable to create connection", e);
            throw new RuntimeException(e);
        }

        try {
            jmsConnection.start();
        } catch (JMSException e) {
            LOG.error("Unable to start connection", e);
            closeSafely(jmsConnection);
            jmsConnection = null;
        }
    }

    @PreDestroy
    public void shutdown() {
        closeSafely(jmsConnection);
    }


    @Programmatic
    public void publish(final EventMetadata metadata, final EventPayload payload) {

        if(payload instanceof EventPayloadForActionInvocation) {
            publishActionInvocation(metadata, (EventPayloadForActionInvocation) payload);

        } else if(payload instanceof EventPayloadForObjectChanged) {
            publishObjectChanged(metadata, (EventPayloadForObjectChanged) payload);
        }

    }

    private void publishActionInvocation(
            final EventMetadata metadata,
            final EventPayloadForActionInvocation payload) {

        final ActionInvocationMementoDto aim = asActionInvocationMementoDto(metadata, payload);
        send(metadata, aim);
    }

    private ActionInvocationMementoDto asActionInvocationMementoDto(final EventMetadata metadata, final EventPayloadForActionInvocation payload) {
        final ActionInvocationMementoDto aim = ActionInvocationMementoUtils.newDto();

        final String actionIdentifier = metadata.getActionIdentifier();
        ActionInvocationMementoUtils.setMetadata(
                aim,
                metadata.getTransactionId(),
                metadata.getSequence(),
                metadata.getJavaSqlTimestamp(),
                metadata.getTargetClass(),
                metadata.getTargetAction(),
                actionIdentifier,
                metadata.getTarget().getObjectType(),
                metadata.getTarget().getIdentifier(),
                metadata.getTitle(),
                metadata.getUser()
        );

        final List<String> parameterNames = metadata.getActionParameterNames();
        final List<Class<?>> parameterTypes = metadata.getActionParameterTypes();
        final Class<?> returnType = metadata.getActionReturnType();
        final List<Object> arguments = payload.getArguments();
        for (int paramNum = 0; paramNum < parameterNames.size(); paramNum++) {
            final String parameterName = parameterNames.get(paramNum);
            final Class<?> parameterType = parameterTypes.get(paramNum);
            final Object arg = arguments.get(paramNum);
            if(!ActionInvocationMementoUtils.addArgValue(aim, parameterName, parameterType, arg)) {
                ActionInvocationMementoUtils.addArgReference(aim, parameterName, bookmarkService.bookmarkFor(arg));
            }
        }
        final Object result = payload.getResult();
        if(result != null) {
            metadata.getActionParameterTypes();
        }
        if(!ActionInvocationMementoUtils.addReturnValue(aim, returnType, result)) {
            ActionInvocationMementoUtils.addReturnReference(aim, bookmarkService.bookmarkFor(result));
        }
        return aim;
    }

    private void send(final EventMetadata metadata, final ActionInvocationMementoDto aim) {
        Session session = null;
        try {

            final String aimXml = ActionInvocationMementoUtils.toXml(aim);
            session = jmsConnection.createSession(transacted, Session.SESSION_TRANSACTED);
            TextMessage message = session.createTextMessage(aimXml);

            message.setJMSMessageID(metadata.getId());
            message.setJMSType(metadata.getActionIdentifier());

            LOG.info("Sending JMS message, id:" + metadata.getId() + "; type:" + message.getJMSType());
            final Queue queue = session.createQueue(actionInvocationsQueueName);
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(message);

            session.commit();

        } catch (JMSException e) {
            rollback(session);
            throw new ApplicationException("Failed to publish message", e);
        } finally {
            if(session != null) {
                closeSafely(session);
            }
        }
    }


    private void publishObjectChanged(
            final EventMetadata metadata,
            final EventPayloadForObjectChanged payload) {
        // currently a no-op
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

    ///////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////


    public static interface Handle {
        public void close();
    }

    static class CSQC implements Handle {
        private final Connection connection;
        private final Session session;
        private final Queue queue;
        private final MessageConsumer messageConsumer;

        public CSQC(final Connection connection, final Session session, final Queue queue, final MessageConsumer messageConsumer) {
            this.connection = connection;
            this.session = session;
            this.queue = queue;
            this.messageConsumer = messageConsumer;
        }

        public Connection getConnection() {
            return connection;
        }

        public Session getSession() {
            return session;
        }

        public Queue getQueue() {
            return queue;
        }

        public MessageConsumer getMessageConsumer() {
            return messageConsumer;
        }

        public void close() {
            if(getMessageConsumer() != null) {
                try {
                    getMessageConsumer().close();
                } catch (JMSException e) {
                }
            }
            if(getSession() != null) {
                try {
                    getSession().close();
                } catch (JMSException e) {
                }
            }
            if(getConnection() != null) {
                try {
                    getConnection().close();
                } catch (JMSException e) {
                }
            }
        }
    }

    /**
     * not API
     */
    @Programmatic
    public Handle subscribe(final MessageListener messageListener) throws JMSException {

        final Connection connection = jmsConnectionFactory.createConnection();

        final Session session = jmsConnection.createSession(transacted, Session.SESSION_TRANSACTED);
        Queue queue = session.createQueue(actionInvocationsQueueName);

        final MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(messageListener);

        return new CSQC(connection, session, queue, consumer);
    }

    public void unsubscribe(final Handle handle) {
        handle.close();
    }



    ///////////////////////////////////////////////////
    // Helper
    ///////////////////////////////////////////////////

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


    ///////////////////////////////////////////////////
    // Dependencies
    ///////////////////////////////////////////////////

    // unused.
    private EventSerializer eventSerializer;

    @Programmatic
    public void setEventSerializer(EventSerializer eventSerializer) {
        this.eventSerializer = eventSerializer;
    }

    @Inject
    BookmarkService bookmarkService;

}
