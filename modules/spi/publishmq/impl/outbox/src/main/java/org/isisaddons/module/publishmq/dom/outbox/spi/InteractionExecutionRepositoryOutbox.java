package org.isisaddons.module.publishmq.dom.outbox.spi;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService2;
import org.apache.isis.applib.services.iactn.Interaction;
import org.apache.isis.applib.services.publish.EventMetadata;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.schema.common.v1.InteractionType;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.utils.InteractionDtoUtils;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEvent;
import org.isisaddons.module.publishmq.dom.outbox.events.PublishedEventType;
import org.isisaddons.module.publishmq.dom.servicespi.InteractionExecutionRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class InteractionExecutionRepositoryOutbox implements InteractionExecutionRepository {

    @Override
    @Programmatic
    public void persist(final Interaction.Execution<?, ?> execution) {

        final OutboxEvent outboxEvent = new OutboxEvent();

        outboxEvent.setEventType(eventTypeFor(execution.getInteractionType()));
        outboxEvent.setTransactionId(execution.getInteraction().getTransactionId());
        outboxEvent.setTimestamp(execution.getStartedAt());
        outboxEvent.setSequence(execution.getDto().getSequence());
        outboxEvent.setUser(execution.getDto().getUser());

        outboxEvent.setTarget(bookmarkService2.bookmarkFor(execution.getTarget()));
        outboxEvent.setMemberIdentifier(execution.getMemberIdentifier());

        outboxEvent.setTargetClass(execution.getTargetClass());
        outboxEvent.setTargetAction(execution.getTargetMember());

        final InteractionDto interactionDto = InteractionDtoUtils.newInteractionDto(execution);
        final String xml = InteractionDtoUtils.toXml(interactionDto);

        outboxEvent.setSerializedForm(xml);

        final String title = buildTitle(outboxEvent);
        outboxEvent.setTitle(title);

        repositoryService.persist(outboxEvent);
    }

    private String buildTitle(final OutboxEvent outboxEvent) {

        // nb: not thread-safe
        // formats defined in https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        final TitleBuffer buf = new TitleBuffer();
        buf.append(format.format(outboxEvent.getTimestamp()));
        buf.append(" ").append(outboxEvent.getMemberIdentifier());

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



}
