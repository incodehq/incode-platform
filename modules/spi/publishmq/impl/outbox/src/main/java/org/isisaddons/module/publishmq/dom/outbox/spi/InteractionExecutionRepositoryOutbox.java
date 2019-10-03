package org.isisaddons.module.publishmq.dom.outbox.spi;

import java.sql.Timestamp;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService2;
import org.apache.isis.applib.services.iactn.Interaction;
import org.apache.isis.schema.common.v1.InteractionType;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.utils.InteractionDtoUtils;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEventRepository;
import org.isisaddons.module.publishmq.dom.outbox.events.PublishedEventType;
import org.isisaddons.module.publishmq.dom.servicespi.InteractionExecutionRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class InteractionExecutionRepositoryOutbox implements InteractionExecutionRepository {

    @Override
    @Programmatic
    public void persist(final Interaction.Execution<?, ?> execution) {

        final PublishedEventType eventType = eventTypeFor(execution.getInteractionType());
        final UUID transactionId = execution.getInteraction().getTransactionId();
        final Timestamp startedAt = execution.getStartedAt();
        final int sequence = execution.getDto().getSequence();
        final String user = execution.getDto().getUser();
        final Bookmark target = bookmarkService2.bookmarkFor(execution.getTarget());
        final String memberIdentifier = execution.getMemberIdentifier();
        final String targetClass = execution.getTargetClass();
        final String targetMember = execution.getTargetMember();

        final InteractionDto interactionDto = InteractionDtoUtils.newInteractionDto(execution);
        final String xml = InteractionDtoUtils.toXml(interactionDto);

        outboxEventRepository.upsert(transactionId, sequence, eventType, startedAt, user, target, targetClass,
                memberIdentifier,
                targetMember, xml);
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
    OutboxEventRepository outboxEventRepository;

    @Inject
    BookmarkService2 bookmarkService2;



}
