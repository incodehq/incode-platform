package org.isisaddons.module.publishmq.dom.jdo.status;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

/**
 * Provides supporting functionality for querying
 * {@link StatusMessage status message} entities.
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class StatusMessageRepository {


    //region > findByTransactionId

    @Programmatic
    public List<StatusMessage> findByTransactionId(final UUID transactionId) {
        return repositoryService.allMatches(
                new QueryDefault<>(StatusMessage.class,
                        "findByTransactionId",
                        "transactionId", transactionId));
    }

    @Programmatic
    public List<StatusMessage> findByTransactionIds(final List<UUID> transactionIds) {
        return repositoryService.allMatches(
                new QueryDefault<>(StatusMessage.class,
                        "findByTransactionIds",
                        "transactionIds", transactionIds));
    }

    //endregion

    //region > findByTransactionId

    @Programmatic
    public List<StatusMessage> findByTransactionIdAndSequence(final UUID transactionId, final int sequence) {
        return repositoryService.allMatches(
                new QueryDefault<>(StatusMessage.class,
                        "findByTransactionIdAndSequence",
                        "transactionId", transactionId,
                        "sequence", sequence));
    }
    //endregion

    //region > findByFromAndTo

    @Programmatic
    public List<StatusMessage> findByFromAndTo(
            final LocalDate from,
            final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);

        final Query<StatusMessage> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(StatusMessage.class,
                        "findByTimestampBetween",
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(StatusMessage.class,
                        "findByTimestampAfter",
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(StatusMessage.class,
                        "findByTimestampBefore",
                        "to", toTs);
            } else {
                query = new QueryDefault<>(StatusMessage.class,
                        "find");
            }
        }
        return repositoryService.allMatches(query);
    }

    //endregion

    //region > helpers

    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, int daysOffset) {
        return dt!=null
                ? new Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                : null;
    }

    //endregion

    @Inject
    RepositoryService repositoryService;

}
