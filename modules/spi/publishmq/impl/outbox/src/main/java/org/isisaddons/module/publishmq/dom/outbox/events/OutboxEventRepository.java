package org.isisaddons.module.publishmq.dom.outbox.events;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.publish.EventMetadata;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.TitleBuffer;

/**
 * Provides supporting functionality for querying and persisting
 * {@link OutboxEvent outbox event} entities.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class OutboxEventRepository {

    @Programmatic
    public Optional<OutboxEvent> findByTransactionIdAndSequence(final UUID transactionId, final int sequence) {
        return Optional.ofNullable(
                repositoryService.uniqueMatch(
                    new QueryDefault<>(OutboxEvent.class,
                            "findByTransactionIdAndSequence",
                            "transactionId", transactionId,
                            "sequence", sequence
                    ))
        );
    }

    @Programmatic
    public List<OutboxEvent> findOldest() {
        return repositoryService.allMatches(
                new QueryDefault<>(OutboxEvent.class, "findOldest"));
    }

    @Programmatic
    public OutboxEvent upsert(
            final UUID transactionId, final int sequence, final PublishedEventType eventType,
            final Timestamp startedAt,
            final String user,
            final Bookmark target,
            final String targetClass, final String memberIdentifier,
            final String targetMember,
            final String xml) {

        return findByTransactionIdAndSequence(transactionId, sequence)
                .orElseGet(() -> {

                    final OutboxEvent outboxEvent = new OutboxEvent();

                    outboxEvent.setEventType(eventType);
                    outboxEvent.setTransactionId(transactionId);
                    outboxEvent.setTimestamp(startedAt);
                    outboxEvent.setSequence(sequence);
                    outboxEvent.setUser(user);

                    outboxEvent.setTarget(target);
                    outboxEvent.setMemberIdentifier(memberIdentifier);

                    outboxEvent.setTargetClass(targetClass);
                    outboxEvent.setTargetAction(targetMember);

                    outboxEvent.setSerializedForm(xml);

                    final String title = buildTitle(outboxEvent);
                    outboxEvent.setTitle(title);

                    repositoryService.persist(outboxEvent);

                    return outboxEvent;
                });
    }

    private static String buildTitle(final OutboxEvent outboxEvent) {

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

    @Programmatic
    public boolean deleteByTransactionIdAndSequence(final UUID transactionId, final int sequence) {
        Optional<OutboxEvent> outboxEventIfAny = findByTransactionIdAndSequence(transactionId, sequence);
        if(outboxEventIfAny.isPresent()) {
            repositoryService.removeAndFlush(outboxEventIfAny.get());
            return true;
        } else {
            return false;
        }
    }


    @Inject
    RepositoryService repositoryService;

}
