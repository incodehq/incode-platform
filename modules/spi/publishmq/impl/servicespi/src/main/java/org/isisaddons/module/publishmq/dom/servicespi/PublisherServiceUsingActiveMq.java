package org.isisaddons.module.publishmq.dom.servicespi;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.iactn.Interaction;
import org.apache.isis.applib.services.publish.PublishedObjects;
import org.apache.isis.applib.services.publish.PublisherService;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublisherServiceUsingActiveMq implements PublisherService,
        PublisherServiceUsingActiveMqStatusProvider {

    private static final Logger LOG = LoggerFactory.getLogger(PublisherServiceUsingActiveMq.class);

    public static final String ROOT = "isis.services." + PublisherServiceUsingActiveMq.class.getSimpleName() + ".";
    public static final String KEY_ENABLED = ROOT + "enabled";
    public static final String KEY_ENABLED_DEFAULT = "true";


    @Programmatic
    @lombok.Getter
    private boolean enabled;


    //region > init, shutdown

    @PostConstruct
    public void init(Map<String,String> properties) {

        enabled = properties.getOrDefault(KEY_ENABLED, KEY_ENABLED_DEFAULT).equalsIgnoreCase("true");
        if(!enabled) {
            LOG.warn("Service NOT enabled");
            return;
        }

    }

    @PreDestroy
    public void shutdown() {
    }


    private static void closeSafely(Session session) {
        try {
            session.close();
        } catch (JMSException e) {
            // ignore
        }
    }

    //endregion


    //region > publish (execution)

    @Override
    public void publish(final Interaction.Execution<?, ?> execution) {

        if(!enabled) {
            LOG.info("Service NOT enabled; interaction will not be persisted/propagated");
            return;
        }

        persist(execution);
    }

    private void persist(final Interaction.Execution<?, ?> execution) {
        if (interactionExecutionRepositories == null) {
            return;
        }
        interactionExecutionRepositories.forEach(interactionExecutionRepository -> {
            interactionExecutionRepository.persist(execution);
        });
    }

    //endregion

    //region > publish (published objects)

    @Override
    public void publish(final PublishedObjects publishedObjects) {

        if(!enabled) {
            LOG.info("Service NOT enabled; publishedObjects will not be persisted/propagated");
            return;
        }
        persist(publishedObjects);
    }

    private void persist(final PublishedObjects publishedObjects) {
        if(publishedObjectsRepositories == null) {
            return;
        }
        publishedObjectsRepositories.forEach(publishedObjectsRepository -> {
            publishedObjectsRepository.persist(publishedObjects);
        });
    }

    //endregion



    //region > injected services
    @Inject
    List<PublishedObjectsRepository> publishedObjectsRepositories;

    @Inject
    List<InteractionExecutionRepository> interactionExecutionRepositories;
    //endregion

}
