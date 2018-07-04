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

import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventType;
import org.isisaddons.module.publishmq.dom.servicespi.PublishedObjectsRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PublishedObjectsRepositoryJdo implements PublishedObjectsRepository {

    @Override
    @Programmatic
    public void persist(final PublishedObjects publishedObjects) {

        final PublishedEvent publishedEvent = new PublishedEvent();

        publishedEvent.setEventType(PublishedEventType.CHANGED_OBJECTS);
        publishedEvent.setTransactionId(publishedObjects.getTransactionId());
        publishedEvent.setTimestamp(publishedObjects.getCompletedAt());
        publishedEvent.setUser(publishedObjects.getUsername());

        publishedEvent.setTarget(null);
        publishedEvent.setTargetClass(null);
        publishedEvent.setMemberIdentifier(null);
        publishedEvent.setTargetAction(null);

        final ChangesDto changesDto = publishedObjects.getDto();

        publishedEvent.setSequence(changesDto.getSequence());

        final String xml = ChangesDtoUtils.toXml(changesDto);

        publishedEvent.setSerializedForm(xml);

        final String title = buildTitle(publishedObjects);
        publishedEvent.setTitle(title);

        repositoryService.persist(publishedEvent);
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
