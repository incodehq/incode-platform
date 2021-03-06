package org.isisaddons.module.command.dom;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

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
import org.apache.isis.applib.services.command.CommandWithDto;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.cmd.v1.CommandsDto;
import org.apache.isis.schema.cmd.v1.MapDto;
import org.apache.isis.schema.common.v1.OidDto;
import org.apache.isis.schema.utils.CommandDtoUtils;
import org.apache.isis.schema.utils.jaxbadapters.JavaSqlTimestampXmlGregorianCalendarAdapter;

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

    //region > findForegroundSince

    /**
     * Intended to support the replay of commands on a slave instance of the application.
     *
     * This finder returns all (completed) {@link CommandJdo}s started after the command with the specified
     * transaction Id.  The number of commands returned can be limited so that they can be applied in batches.
     *
     * If the provided transactionId is null, then only a single {@link CommandJdo command} is returned.  This is
     * intended to support the case when the slave does not yet have any {@link CommandJdo command}s replicated.
     * In practice this is unlikely; typically we expect that the slave will be set up to run against a copy of the
     * master instance's DB (restored from a backup), in which case there will already be a {@link CommandJdo command}
     * representing the current high water mark on the slave.
     *
     * If the transaction id is not null but the corresponding {@link CommandJdo command} is not found, then
     * <tt>null</tt> is returned. In the replay scenario the caller will probably interpret this as an error because
     * it means that the high water mark on the slave is inaccurate, referring to a non-existent
     * {@link CommandJdo command} on the master.
     *
     * @param transactionId - the identifier of the {@link CommandJdo command} being the replay hwm (using {@link #findReplayHwm()} on the slave), or null if no HWM was found there.
     * @param batchSize - to restrict the number returned (so that replay commands can be batched).
     *
     * @return
     */
    public List<CommandJdo> findForegroundSince(final UUID transactionId, final Integer batchSize) {
        if(transactionId == null) {
            return findForegroundFirst();
        }
        final CommandJdo from = findByTransactionIdElseNull(transactionId);
        if(from == null) {
            return null;
        }
        return findForegroundSince(from.getTimestamp(), batchSize);
    }

    private List<CommandJdo> findForegroundFirst() {
        CommandJdo firstCommandIfAny = repositoryService.firstMatch(
                new QueryDefault<>(CommandJdo.class, "findForegroundFirst"));
        return asList(firstCommandIfAny);
    }

    private static <T> List<T> asList(@Nullable final T objIfAny) {
        return objIfAny != null
                ? Collections.singletonList(objIfAny)
                : Collections.emptyList();
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

    private List<CommandJdo> findForegroundSince(final Timestamp timestamp, final Integer batchSize) {
        final QueryDefault<CommandJdo> q = new QueryDefault<>(
                CommandJdo.class,
                "findForegroundSince",
                "timestamp", timestamp);

        // DN generates incorrect SQL for SQL Server if count set to 1; so we set to 2 and then trim
        if(batchSize != null) {
            q.withCount(batchSize == 1 ? 2 : batchSize);
        }
        final List<CommandJdo> commandJdos = repositoryService.allMatches(q);
        return batchSize == 1 && commandJdos.size() > 1
                    ? commandJdos.subList(0,1)
                    : commandJdos;
    }
    //endregion

    //region > findReplayHwm
    @Programmatic
    public CommandJdo findReplayHwm() {

        // most recent replayable command, replicated from master to slave
        // this may or may not
        CommandJdo replayableHwm = repositoryService.firstMatch(
                new QueryDefault<>(CommandJdo.class, "findReplayableHwm"));
        if(replayableHwm != null) {
            return replayableHwm;
        }

        // otherwise, the most recent completed command, run in the foreground
        // on the slave, this corresponds to a command restored from a copy of the production database
        CommandJdo restoredFromDbHwm = repositoryService.firstMatch(
                new QueryDefault<>(CommandJdo.class, "findForegroundHwm"));

        return restoredFromDbHwm;
    }

    //endregion

    //region > findBackgroundCommandsNotYetStarted

    @Programmatic
    public List<CommandJdo> findBackgroundCommandsNotYetStarted() {
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class,
                        "findBackgroundCommandsNotYetStarted"));
    }
    //endregion

    //region > findBackgroundCommandsByParent

    @Programmatic
    public List<CommandJdo> findBackgroundCommandsByParent(final CommandJdo parent) {
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class,
                        "findBackgroundCommandsByParent",
                        "parent", parent));
    }

    //endregion

    //region > findReplayedOnSlave
    @Programmatic
    public List<CommandJdo> findReplayedOnSlave() {
        return repositoryService.allMatches(
                new QueryDefault<>(CommandJdo.class, "findReplayableMostRecentStarted"));
    }

    //endregion

    //region > saveForReplay (CommandDTO)

    @Programmatic
    public List<CommandJdo> saveForReplay(final CommandsDto commandsDto) {
        List<CommandDto> commandDto = commandsDto.getCommandDto();
        List<CommandJdo> commands = Lists.newArrayList();
        for (final CommandDto dto : commandDto) {
            commands.add(saveForReplay(dto));
        }
        return commands;
    }

    @Programmatic
    public CommandJdo saveForReplay(final CommandDto dto) {

        final MapDto userData = dto.getUserData();
        if (userData == null ) {
            throw new IllegalStateException(String.format(
                    "Can only persist DTOs with additional userData; got: \n%s",
                    CommandDtoUtils.toXml(dto)));
        }

        final CommandJdo commandJdo = repositoryService.instantiate(CommandJdo.class);

        commandJdo.setTransactionId(UUID.fromString(dto.getTransactionId()));
        commandJdo.setTimestamp(JavaSqlTimestampXmlGregorianCalendarAdapter.parse(dto.getTimestamp()));
        commandJdo.setUser(dto.getUser());
        commandJdo.setExecuteIn(org.apache.isis.applib.annotation.Command.ExecuteIn.REPLAYABLE);

        commandJdo.setTargetClass(CommandDtoUtils.getUserData(dto, CommandWithDto.USERDATA_KEY_TARGET_CLASS));
        commandJdo.setTargetAction(CommandDtoUtils.getUserData(dto, CommandWithDto.USERDATA_KEY_TARGET_ACTION));
        commandJdo.setArguments(CommandDtoUtils.getUserData(dto, CommandWithDto.USERDATA_KEY_ARGUMENTS));

        commandJdo.setReplayState(ReplayState.PENDING);
        commandJdo.setPersistHint(true);

        final OidDto firstTarget = dto.getTargets().getOid().get(0);
        commandJdo.setTargetStr(Bookmark.from(firstTarget).toString());
        commandJdo.setMemento(CommandDtoUtils.toXml(dto));
        commandJdo.setMemberIdentifier(dto.getMember().getMemberIdentifier());

        persist(commandJdo);

        return commandJdo;
    }

    @Programmatic
    public void persist(final CommandJdo commandJdo) {

        withSafeTargetStr(commandJdo);
        withSafeResultStr(commandJdo);

        repositoryService.persist(commandJdo);
    }

    @Programmatic
    public void persistIfHinted(final CommandJdo commandJdo) {
        withSafeTargetStr(commandJdo);
        withSafeResultStr(commandJdo);

        if(commandJdo.shouldPersist()) {
            repositoryService.persist(commandJdo);
        }

    }

    private static void withSafeTargetStr(final CommandJdo commandJdo) {
        if (tooLong(commandJdo.getTargetStr())) {
            commandJdo.setTargetStr(null);
        }
    }
    private static void withSafeResultStr(final CommandJdo commandJdo) {
        if (tooLong(commandJdo.getResultStr())) {
            commandJdo.setResultStr(null);
        }
    }

    // can't store result if too long (eg view models)
    private static boolean tooLong(final String str) {
        return str != null && str.length() > JdoColumnLength.BOOKMARK;
    }

    //endregion

    //region > injected services
    @javax.inject.Inject
    CommandServiceJdo commandService;

    @javax.inject.Inject
    CommandContext commandContext;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;
    //endregion

}
