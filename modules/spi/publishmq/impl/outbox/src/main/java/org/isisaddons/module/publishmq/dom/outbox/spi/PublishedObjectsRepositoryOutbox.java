package org.isisaddons.module.publishmq.dom.jdo.spi;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.publish.PublishedObjects;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.schema.chg.v1.ChangesDto;
import org.apache.isis.schema.utils.ChangesDtoUtils;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEvent;
import org.isisaddons.module.publishmq.dom.outbox.events.PublishedEventType;
import org.isisaddons.module.publishmq.dom.servicespi.PublishedObjectsRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishedObjectsRepositoryOutbox implements PublishedObjectsRepository {

    @Override
    @Programmatic
    public void persist(final PublishedObjects publishedObjects) {

        final OutboxEvent outboxEvent = new OutboxEvent();

        outboxEvent.setEventType(PublishedEventType.CHANGED_OBJECTS);
        outboxEvent.setTransactionId(publishedObjects.getTransactionId());
        outboxEvent.setTimestamp(publishedObjects.getCompletedAt());
        outboxEvent.setUser(publishedObjects.getUsername());

        outboxEvent.setTarget(null);
        outboxEvent.setTargetClass(null);
        outboxEvent.setMemberIdentifier(null);
        outboxEvent.setTargetAction(null);

        final ChangesDto changesDto = publishedObjects.getDto();

        outboxEvent.setSequence(changesDto.getSequence());

        final String xml = ChangesDtoUtils.toXml(changesDto);

        outboxEvent.setSerializedForm(xml);

        final String title = buildTitle(publishedObjects);
        outboxEvent.setTitle(title);

        repositoryService.persist(outboxEvent);
    }

    private String buildTitle(final PublishedObjects publishedObjects) {
        final StringBuilder buf = new StringBuilder();
        buf.append(titleService.titleOf(PublishedEventType.CHANGED_OBJECTS))
                .append(" (")
                .append(publishedObjects.getNumberLoaded()).append( " loaded, ")
                .append(publishedObjects.getNumberCreated()).append( " created, ")
                .append(publishedObjects.getNumberUpdated()).append( " updated, ")
                .append(publishedObjects.getNumberUpdated()).append( " deleted, ")
                .append(publishedObjects.getNumberPropertiesModified()).append( " props modified")
                .append(")");
        return buf.toString();
    }

    @Inject
    RepositoryService repositoryService;

    @Inject
    TitleService titleService;

}
