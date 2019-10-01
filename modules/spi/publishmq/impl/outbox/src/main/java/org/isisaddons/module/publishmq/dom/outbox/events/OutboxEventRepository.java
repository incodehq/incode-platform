package org.isisaddons.module.publishmq.dom.outbox.events;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

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
    public List<OutboxEvent> findOldestUser() {
        return repositoryService.allMatches(
                new QueryDefault<>(OutboxEvent.class, "findOldest"));
    }


    @Programmatic
    public boolean deleteByTransactionIdAndSequence(final UUID transactionId, final int sequence) {
        Optional<OutboxEvent> outboxEventIfAny = findByTransactionIdAndSequence(transactionId, sequence);
        if(outboxEventIfAny.isPresent()) {
            repositoryService.removeAndFlush(outboxEventIfAny);
            return true;
        } else {
            return false;
        }
    }


    @Inject
    RepositoryService repositoryService;

}
