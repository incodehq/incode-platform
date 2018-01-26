package org.isisaddons.module.publishmq.dom.jdo.spi;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService2;
import org.apache.isis.applib.services.iactn.Interaction;
import org.apache.isis.applib.services.publish.EventMetadata;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.schema.common.v1.InteractionType;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.utils.InteractionDtoUtils;

import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventType;
import org.isisaddons.module.publishmq.dom.servicespi.InteractionExecutionRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class InteractionExecutionRepositoryJdo implements InteractionExecutionRepository {

    @Override
    @Programmatic
    public void persist(final Interaction.Execution<?, ?> execution) {

        final PublishedEvent publishedEvent = new PublishedEvent();

        publishedEvent.setEventType(eventTypeFor(execution.getInteractionType()));
        publishedEvent.setTransactionId(execution.getInteraction().getTransactionId());
        publishedEvent.setTimestamp(execution.getStartedAt());
        publishedEvent.setSequence(execution.getDto().getSequence());
        publishedEvent.setUser(execution.getDto().getUser());

        publishedEvent.setTarget(bookmarkService2.bookmarkFor(execution.getTarget()));
        publishedEvent.setMemberIdentifier(execution.getMemberIdentifier());

        publishedEvent.setTargetClass(execution.getTargetClass());
        publishedEvent.setTargetAction(execution.getTargetMember());

        final InteractionDto interactionDto = InteractionDtoUtils.newInteractionDto(execution);
        final String xml = InteractionDtoUtils.toXml(interactionDto);

        publishedEvent.setSerializedForm(xml);

        final String title = buildTitle(publishedEvent, execution);
        publishedEvent.setTitle(title);

        repositoryService.persist(publishedEvent);
    }

    private String buildTitle(final PublishedEvent publishedEvent, final Interaction.Execution<?, ?> execution) {
        final StringBuilder buf = new StringBuilder();

        buf.append(titleService.titleOf(publishedEvent.getEventType()))
            .append(" of '").append(publishedEvent.getTargetAction())
            .append("' on ").append(titleService.titleOf(execution.getTarget()));
        String s = buf.toString();
        if (s.length() > EventMetadata.TitleType.Meta.MAX_LEN) {
            return s.substring(0, EventMetadata.TitleType.Meta.MAX_LEN - 3) + "...";
        }
        return s;
    }

    private PublishedEventType eventTypeFor(final InteractionType interactionType) {
        switch (interactionType) {

        case ACTION_INVOCATION:
            return PublishedEventType.ACTION_INVOCATION;
        case PROPERTY_EDIT:
            return PublishedEventType.PROPERTY_EDIT;
        default:
            // should never occur
            throw new IllegalArgumentException(String.format(
                    "InteractionType '%s' not recognized", interactionType));
        }
    }

    @Inject
    RepositoryService repositoryService;

    @Inject
    BookmarkService2 bookmarkService2;

    @Inject
    TitleService titleService;


}
