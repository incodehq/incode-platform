package org.isisaddons.module.command.dom;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.datanucleus.query.typesafe.TypesafeQuery;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.CommandContext;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.cmd.v1.MapDto;
import org.apache.isis.schema.common.v1.OidDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

/**
 * Provides supporting functionality for querying and persisting
 * {@link CommandJdo command} entities.
 *
 * <p>
 * This supporting service with no UI and no side-effects, and is there are no other implementations of the service,
 * thus has been annotated with {@link org.apache.isis.applib.annotation.DomainService}.  This means that there is no
 * need to explicitly register it as a service (eg in <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommandServiceJdoRepository {

    //region > findByFromAndTo

    @Programmatic
    public List<CommandJdo> findByFromAndTo(
            final LocalDate from, final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);
        
        final Query<CommandJdo> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(CommandJdo.class,
                        "findByTimestampBetween", 
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(CommandJdo.class,
                        "findByTimestampAfter", 
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(CommandJdo.class,
                        "findByTimestampBefore", 
                        "to", toTs);
            } else {
                query = new QueryDefault<>(CommandJdo.class,
                        "find");
            }
        }
        return repositoryService.allMatches(query);
    }

    //endregion

    //region > findByTransactionId

    @Programmatic
    public CommandJdo findByTransactionId(final UUID transactionId) {
        persistCurrentCommandIfRequired();
        // TODO: delegate to type-safe query in findByTransactionIdElseNull helper method
        return repositoryService.firstMatch(
                new QueryDefault<>(CommandJdo.class,
                        "findByTransactionId", 
                        "transactionId", transactionId));
    }

    //endregion

    //region > findCurrent

    @Programmatic
    public List<CommandJdo> findCurrent() {
        persistCurrentCommandIfRequired();
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class, "findCurrent"));
    }

    //endregion

    //region > findCompleted

    @Programmatic
    public List<CommandJdo> findCompleted() {
        persistCurrentCommandIfRequired();
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class, "findCompleted"));
    }

    private void persistCurrentCommandIfRequired() {
        if(commandContext == null || commandService == null) {
            return;
        } 
        final Command command = commandContext.getCommand();
        final CommandJdo commandJdo = commandService.asUserInitiatedCommandJdo(command);
        if(commandJdo == null) {
            return;
        }
        repositoryService.persist(commandJdo);
    }

    //endregion

    //region > findByTargetAndFromAndTo

    @Programmatic
    public List<CommandJdo> findByTargetAndFromAndTo(
            final Bookmark target, final LocalDate from, final LocalDate to) {
        final String targetStr = target.toString();
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);
        
        final Query<CommandJdo> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(CommandJdo.class,
                        "findByTargetAndTimestampBetween", 
                        "targetStr", targetStr,
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(CommandJdo.class,
                        "findByTargetAndTimestampAfter", 
                        "targetStr", targetStr,
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(CommandJdo.class,
                        "findByTargetAndTimestampBefore", 
                        "targetStr", targetStr,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(CommandJdo.class,
                        "findByTarget", 
                        "targetStr", targetStr);
            }
        }
        return repositoryService.allMatches(query);
    }

    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, int daysOffset) {
        return dt!=null
                ?new java.sql.Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                :null;
    }

    //endregion

    //region > findRecentByUser

    @Programmatic
    public List<CommandJdo> findRecentByUser(final String user) {
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class, "findRecentByUser", "user", user));

    }
    //endregion

    //region > findRecentByTarget

    @Programmatic
    public List<CommandJdo> findRecentByTarget(final Bookmark target) {
        final String targetStr = target.toString();
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class, "findRecentByTarget", "targetStr", targetStr));
    }
    //endregion

    //region > findRecentBackgroundByTarget
    @Programmatic
    public List<CommandJdo> findRecentBackgroundByTarget(Bookmark target) {
        final String targetStr = target.toString();
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class, "findRecentBackgroundByTarget", "targetStr", targetStr));
    }
    //endregion

    //region > findSince

    /**
     * Intended to support the replay of commands on a slave instance of the application.
     *
     * This finder returns all {@link CommandJdo}s started after the command with the specified transaction.
     * The number of commands returned can be limited so that they can be applied in batches.
     *
     * If the transactionId is null, then only a single Command is returned.  This is intended to support the case
     * when the slave does not yet have any Commands replicated.  In practice this is unlikely; typically we expect
     * that the slave will be set up to run against a copy of the master instance's DB (restored from a backup), in
     * which case there will already be a Command representing the current high water mark on the slave.
     *
     * If the transaction id is not null but the corresponding Command is not found, then <tt>null</tt> is returned.
     * In the replay scenario the caller will probably interpret this as an error because it means that the high
     * water mark on the slave is inaccurate, referring to a non-existent Command on the master.
     *
     * @param transactionId
     * @param count
     * @return
     */
    public List<CommandJdo> findSince(final UUID transactionId, final Integer count) {
        if(transactionId == null) {
            return findFirst();
        }
        final CommandJdo from = findByTransactionIdElseNull(transactionId);
        if(from == null) {
            return null;
        }
        return findSince(from.getStartedAt(), count);
    }

    private List<CommandJdo> findFirst() {
        final QueryDefault<CommandJdo> q = new QueryDefault<>(
                CommandJdo.class,
                "findAscending")
            .withCount(1);
        return repositoryService.allMatches(q);
    }

    private CommandJdo findByTransactionIdElseNull(final UUID transactionId) {
        TypesafeQuery<CommandJdo> q = isisJdoSupport.newTypesafeQuery(CommandJdo.class);
        final QCommandJdo cand = QCommandJdo.candidate();
        q = q.filter(
                cand.transactionId.eq(q.parameter("transactionId", UUID.class))
        );
        q.setParameter("transactionId", transactionId);
        return q.executeUnique();
    }

    private List<CommandJdo> findSince(final Timestamp from, final Integer count) {
        final QueryDefault<CommandJdo> q = new QueryDefault<>(
                CommandJdo.class,
                "findByTimestampAfterExcludingAndAscending",
                "from", from);
        if(count != null) {
            q.withCount(count);
        }
        return repositoryService.allMatches(q);
    }
    //endregion

    //region > save (CommandDTO)

    @Programmatic
    public void save(final CommandDto dto) {

        final String targetClass;
        final String targetAction;
        final String arguments;
        final Timestamp timestamp;

        final MapDto userData = dto.getUserData();
        if (userData == null) {
            throw new IllegalStateException(String.format(
                    "Can only persist DTOs with additional userData; got: \n%s",
                    CommandDtoUtils.toXml(dto)));
        }

        final Map<String, String> userDataMap =
                userData.getEntry().stream()
                        .collect(Collectors.toMap(MapDto.Entry::getKey, MapDto.Entry::getValue));
        targetClass = userDataMap.get(CommandJdo.DTO_USERDATA_KEY_TARGET_CLASS);
        targetAction = userDataMap.get(CommandJdo.DTO_USERDATA_KEY_TARGET_ACTION);
        arguments = userDataMap.get(CommandJdo.DTO_USERDATA_KEY_ARGUMENTS);
        timestamp = new Timestamp(Long.parseLong(userDataMap.get(CommandJdo.DTO_USERDATA_KEY_TIMESTAMP_TIME)));

        final CommandJdo commandJdo = repositoryService.instantiate(CommandJdo.class);

        final UUID transactionId = UUID.fromString(dto.getTransactionId());
        commandJdo.setTransactionId(transactionId);

        final String user = dto.getUser();
        commandJdo.setUser(user);

        commandJdo.setTimestamp(timestamp);

        // TODO: perhaps we should introduce some other sort of mode, eg REPLAY ?
        commandJdo.setExecuteIn(org.apache.isis.applib.annotation.Command.ExecuteIn.BACKGROUND);

        commandJdo.setTargetClass(targetClass);
        commandJdo.setTargetAction(targetAction);

        commandJdo.setArguments(arguments);

        commandJdo.setPersistHint(true);

        final OidDto firstTarget = dto.getTargets().getOid().get(0);
        commandJdo.setTargetStr(Bookmark.from(firstTarget).toString());
        commandJdo.setMemento(CommandDtoUtils.toXml(dto));
        commandJdo.setMemberIdentifier(dto.getMember().getMemberIdentifier());

        repositoryService.persist(commandJdo);
    }

    //endregion

    @javax.inject.Inject
    CommandServiceJdo commandService;
    
    @javax.inject.Inject
    CommandContext commandContext;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
