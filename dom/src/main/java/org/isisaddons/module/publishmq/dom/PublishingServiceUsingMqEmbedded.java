package org.isisaddons.module.publishmq.dom;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.security.JaasAuthenticationPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
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

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishingServiceUsingMqEmbedded implements PublishingService {

    private final static Logger LOG = LoggerFactory.getLogger(PublishingServiceUsingMqEmbedded.class);

    private static final String ROOT = PublishMqModule.class.getPackage().getName() + ".";

    private static final String JAVA_NAMING_FACTORY_INITIAL = "java.naming.factory.initial";
    private static final String JAVA_NAMING_PROVIDER_URL = "java.naming.provider.url";

    private final static String NAMING_FACTORY = ROOT + JAVA_NAMING_FACTORY_INITIAL;
    private final static String PROVIDER_URL = ROOT + JAVA_NAMING_PROVIDER_URL;
    
    private static final String CONNECTION_FACTORY = ROOT + "connectionFactory";
    private static final String CONNECTION_FACTORY_DEFAULT = "ConnectionFactory";
    
    private final static String QUEUE = ROOT + "queue";
    private final static String QUEUE_DEFAULT = "queue";


    private InitialContext jndiContext;
    
    private ConnectionFactory jmsConnectionFactory;
    private Connection jmsConnection;

    private EventSerializer eventSerializer;

    private String queue;

    private boolean transacted = true;
    
    @Programmatic
    @PostConstruct
    public void init(Map<String,String> properties) {
        
//        final String namingFactory = properties.get(NAMING_FACTORY);
//        if (namingFactory == null) {
//            LOG.info("Naming factory not configured, skipping");
//        }
//        final String providerUrl = properties.get(PROVIDER_URL);
//        if (providerUrl == null) {
//            LOG.info("Provider URL not configured, skipping");
//        }
//        String connectionFactory = properties.get(CONNECTION_FACTORY);
//        if (connectionFactory == null) {
//            LOG.info("Provider URL not configured, using default");
//            connectionFactory = CONNECTION_FACTORY_DEFAULT;
//        }
//        queue = properties.get(QUEUE);
//        if(queue == null) {
//            LOG.info("Queue name not configured, using default");
//            queue = QUEUE_DEFAULT;
//        }
//
//        LOG.info("Naming factory    : " + namingFactory);
//        LOG.info("Provider URL      : " + providerUrl);
//        LOG.info("Connection factory: " + connectionFactory);
//        LOG.info("Queue             : " + queue);
//
//        Hashtable<String, String> ctxProps = new Hashtable<String, String>();
//        if (namingFactory != null) {
//            ctxProps.put(JAVA_NAMING_FACTORY_INITIAL, namingFactory);
//        }
//        if (providerUrl != null) {
//            ctxProps.put(JAVA_NAMING_PROVIDER_URL, providerUrl);
//        }
//
//        try {
//            jndiContext = new InitialContext(ctxProps);
//        } catch (NamingException e) {
//            LOG.error(e.getExplanation());
//            return;
//        }
//        try {
//            jmsConnectionFactory = (ConnectionFactory) jndiContext.lookup(connectionFactory);
//        } catch (NamingException e) {
//            LOG.error(e.getExplanation(), e);
//            return;
//        }
//        LOG.info("Successfully looked up '" + connectionFactory + "'");

        BrokerService broker = new BrokerService();
        broker.setBrokerName("fred");
        broker.setUseShutdownHook(false);
        //Add plugin
        broker.setPlugins(new BrokerPlugin[]{new JaasAuthenticationPlugin()});
        //Add a network connection

        try {
            TransportConnector connector = new TransportConnector();
            connector.setUri(new URI("tcp://localhost:61616"));
            broker.addConnector(connector);
            broker.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");

        try {
            jmsConnection = jmsConnectionFactory.createConnection();
        } catch (JMSException e) {
            LOG.error("Unable to create connection", e);
            return;
        }
        
        try {
            jmsConnection.start();
        } catch (JMSException e) {
            LOG.error("Unable to start connection", e);
            closeSafely(jmsConnection);
            jmsConnection = null;
        }
    }

    @Programmatic
    @PreDestroy
    public void shutdown() {
        closeSafely(jmsConnection);
    }


    @Programmatic
    public void publish(final EventMetadata metadata, final EventPayload payload) {
        //String message = eventSerializer.serialize(metadata, payload).toString();

        if(payload instanceof EventPayloadForActionInvocation) {
            publishActionInvocation(metadata, (EventPayloadForActionInvocation) payload);

        } else if(payload instanceof EventPayloadForObjectChanged) {
            publishObjectChanged(metadata, (EventPayloadForObjectChanged) payload);
        }

    }

    private void publishActionInvocation(
            final EventMetadata metadata,
            final EventPayloadForActionInvocation payload) {

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
        final List<Object> arguments = payload.getArguments();
        for (int paramNum = 0; paramNum < parameterNames.size(); paramNum++) {
            final String parameterName = parameterNames.get(paramNum);
            final Class<?> parameterType = parameterTypes.get(paramNum);
            final Object arg = arguments.get(paramNum);
            if(!ActionInvocationMementoUtils.addArgValue(aim, parameterName, parameterType, arg)) {
                ActionInvocationMementoUtils.addArgReference(aim, parameterName, bookmarkService.bookmarkFor(arg));
            }
        }
        final String xml = ActionInvocationMementoUtils.toXml(aim);
        publishMessage(xml);
    }

    private void publishObjectChanged(
            final EventMetadata metadata,
            final EventPayloadForObjectChanged payload) {
        // currently a no-op
    }

    @Programmatic
    void publishMessage(
            final String messageStr) {
        if(jmsConnection == null) {
            throw new ApplicationException("Unable to publish (init failed, no JMS connection)");
        }

        publish(messageStr, null);
    }

    private void publish(final String messageStr, final EventMetadata metadata) {
        Session session = null;
        try {
            // TODO: hacking...
//            session = jmsConnection.createSession(transacted, Session.SESSION_TRANSACTED);
            session = jmsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);
    
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            
            TextMessage message = session.createTextMessage(messageStr);
            if(metadata != null) {
                message.setJMSMessageID(metadata.getId());
            }
    
            LOG.info("Sent message: "+ message.hashCode());
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

    
    ///////////////////////////////////////////////////
    // Dependencies
    ///////////////////////////////////////////////////

    
    @Hidden
    public void setEventSerializer(EventSerializer eventSerializer) {
        this.eventSerializer = eventSerializer;
    }


    @Inject
    private BookmarkService bookmarkService;

}
