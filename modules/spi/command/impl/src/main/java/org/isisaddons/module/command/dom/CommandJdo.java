package org.isisaddons.module.command.dom;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Command.ExecuteIn;
import org.apache.isis.applib.annotation.Command.Persistence;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.HasUsername;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.Command3;
import org.apache.isis.applib.services.command.CommandWithDto;
import org.apache.isis.applib.services.eventbus.ActionInteractionEvent;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;
import org.apache.isis.objectstore.jdo.applib.service.Util;
import org.apache.isis.schema.cmd.v1.CommandDto;

import org.isisaddons.module.command.CommandModule;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.APPLICATION,
        schema = "isiscommand",
        table="Command")
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="findByTransactionId",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE transactionId == :transactionId "),
    @javax.jdo.annotations.Query(
            name="findBackgroundCommandsByParent",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE parent == :parent "
                    + "&& executeIn == 'BACKGROUND'"),
    @javax.jdo.annotations.Query(
            name="findCurrent",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE completedAt == null "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findCompleted",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE completedAt != null "
                    + "&& executeIn == 'FOREGROUND' "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findRecentBackgroundByTarget",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr "
                    + "&& executeIn == 'BACKGROUND' "
                    + "ORDER BY this.timestamp DESC, transactionId DESC "
                    + "RANGE 0,30"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBetween",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from " 
                    + "&& timestamp <= :to "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampAfter",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBefore",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp <= :to "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTarget",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBetween",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE timestamp >= :from " 
                    + "&&    timestamp <= :to "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampAfter",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE timestamp >= :from "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBefore",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE timestamp <= :to "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="find",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "ORDER BY this.timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findRecentByUser",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE user == :user "
                    + "ORDER BY this.timestamp DESC "
                    + "RANGE 0,30"),
    @javax.jdo.annotations.Query(
            name="findRecentByTarget",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr "
                    + "ORDER BY this.timestamp DESC, transactionId DESC "
                    + "RANGE 0,30"),
    @javax.jdo.annotations.Query(
            name="findForegroundFirst",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE executeIn == 'FOREGROUND' "
                    + "   && timestamp   != null "
                    + "   && startedAt   != null "
                    + "   && completedAt != null "
                    + "ORDER BY this.timestamp ASC "
                    + "RANGE 0,2"),
        // this should be RANGE 0,1 but results in DataNucleus submitting "FETCH NEXT ROW ONLY"
        // which SQL Server doesn't understand.  However, as workaround, SQL Server *does* understand FETCH NEXT 2 ROWS ONLY
    @javax.jdo.annotations.Query(
            name="findForegroundSince",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE executeIn == 'FOREGROUND' "
                    + "   && timestamp > :timestamp "
                    + "   && startedAt != null "
                    + "   && completedAt != null "
                    + "ORDER BY this.timestamp ASC"),
    @javax.jdo.annotations.Query(
            name="findReplayableHwm",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE executeIn == 'REPLAYABLE' "
                    + "ORDER BY this.timestamp DESC "
                    + "RANGE 0,2"),
        // this should be RANGE 0,1 but results in DataNucleus submitting "FETCH NEXT ROW ONLY"
        // which SQL Server doesn't understand.  However, as workaround, SQL Server *does* understand FETCH NEXT 2 ROWS ONLY
    @javax.jdo.annotations.Query(
            name="findForegroundHwm",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE executeIn == 'FOREGROUND' "
                    + "   && startedAt   != null "
                    + "   && completedAt != null "
                    + "ORDER BY this.timestamp DESC "
                    + "RANGE 0,2"),
        // this should be RANGE 0,1 but results in DataNucleus submitting "FETCH NEXT ROW ONLY"
        // which SQL Server doesn't understand.  However, as workaround, SQL Server *does* understand FETCH NEXT 2 ROWS ONLY
    @javax.jdo.annotations.Query(
            name="findBackgroundCommandsNotYetStarted",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE executeIn == 'BACKGROUND' "
                    + "   && startedAt == null "
                    + "ORDER BY this.timestamp ASC "),
        @javax.jdo.annotations.Query(
                name="findReplayableInErrorMostRecent",
                value="SELECT "
                        + "FROM org.isisaddons.module.command.dom.CommandJdo "
                        + "WHERE executeIn   == 'REPLAYABLE' "
                        + "  && (replayState != 'PENDING' || "
                        + "      replayState != 'OK'      || "
                        + "      replayState != 'EXCLUDED'   ) "
                        + "ORDER BY this.timestamp DESC "
                        + "RANGE 0,2"),
    @javax.jdo.annotations.Query(
            name="findReplayableMostRecentStarted",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE executeIn == 'REPLAYABLE' "
                    + "   && startedAt != null "
                    + "ORDER BY this.timestamp DESC "
                    + "RANGE 0,20"),
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(name = "CommandJdo_timestamp_e_s_IDX", members = {"timestamp", "executeIn", "startedAt"}),
        @javax.jdo.annotations.Index(name = "CommandJdo_startedAt_e_c_IDX", members = {"startedAt", "executeIn", "completedAt"}),
})
@DomainObject(
        objectType = "isiscommand.Command",
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        named = "Command"
)
public class CommandJdo extends DomainChangeJdoAbstract implements Command3, HasUsername, CommandWithDto, Comparable<CommandJdo> {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(CommandJdo.class);

    //region > domain event superclasses
    public static abstract class PropertyDomainEvent<T> extends CommandModule.PropertyDomainEvent<CommandJdo, T> { }

    public static abstract class CollectionDomainEvent<T> extends CommandModule.CollectionDomainEvent<CommandJdo, T> { }

    public static abstract class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandJdo> { }
    //endregion

    public CommandJdo() {
        super(ChangeType.COMMAND);
    }

    //region > identification
    public String title() {
        // nb: not thread-safe
        // formats defined in https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        final TitleBuffer buf = new TitleBuffer();
        buf.append(format.format(getTimestamp()));
        buf.append(" ").append(getMemberIdentifier());
        return buf.toString();
    }
    //endregion

    //region > user (property)

    public static class UserDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.USER_NAME)
    @Property(
            domainEvent = UserDomainEvent.class
    )
    @Getter @Setter
    private String user;


    @Programmatic
    public String getUsername() {
        return getUser();
    }

    //endregion

    //region > timestamp (property)


    public static class TimestampDomainEvent extends PropertyDomainEvent<Timestamp> { }

    /**
     * The date/time at which this action was created.
     *
     * <p>
     *
     * <p>The setter is NOT API: intended to be called only by the framework.
     */
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            domainEvent = TimestampDomainEvent.class
    )
    @Getter @Setter
    private Timestamp timestamp;

    //endregion

    //region > executor (programmatic)

    @javax.jdo.annotations.NotPersistent
    @Setter
    private Executor executor;

    @Programmatic
    public Executor getExecutor() {
        return executor;
    }

    //endregion

    //region > executeIn (property)

    public static class ExecuteInDomainEvent extends PropertyDomainEvent<ExecuteIn> { }


    /**
     * Whether the action was invoked explicitly by the user, or scheduled as a background
     * task, or as for some other reason, eg a side-effect of rendering an object due to 
     * get-after-post).
     *
     * Setter is <b>NOT API</b>: intended to be called only by the framework.
     */
    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.Command.EXECUTE_IN)
    @Property(
            domainEvent = ExecuteInDomainEvent.class
    )
    @Getter @Setter
    private ExecuteIn executeIn;

    //endregion

    //region > replayState (property)

    public static class ReplayStateDomainEvent extends PropertyDomainEvent<ReplayState> { }


    /**
     * For a replayed command, what the outcome was.
     *
     * <b>NOT API</b>.
     */
    @javax.jdo.annotations.Column(allowsNull="true", length=10)
    @Property(
            domainEvent = ReplayStateDomainEvent.class
    )
    @Getter @Setter
    private ReplayState replayState;
    @Getter @Setter

    //endregion

    //region > replayState (property)

    public static class ReplayStateFailureReasonDomainEvent extends PropertyDomainEvent<ReplayState> { }


    /**
     * For a {@link ReplayState#FAILED failed} replayed command, what the reason was for the failure.
     *
     * <b>NOT API</b>.
     */
    @javax.jdo.annotations.Column(allowsNull="true", length=255)
    @Property(
            domainEvent = ReplayStateFailureReasonDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            multiLine = 5
    )
    @Getter @Setter
    private String replayStateFailureReason;

    public boolean hideReplayStateFailureReason() {
        return !getReplayState().isFailed();
    }

    //endregion


    //region > parent (property)

    public static class ParentDomainEvent extends PropertyDomainEvent<Command> { }


    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(name="parentTransactionId", allowsNull="true")
    @Property(
            domainEvent = ParentDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES
    )
    @Getter @Setter
    private Command parent;

    //endregion

    //region > transactionId (property)

    public static class TransactionIdDomainEvent extends PropertyDomainEvent<UUID> { }


    /**
     * The unique identifier (a GUID) of the transaction in which this command occurred.
     *
     * The setter is <b>NOT API</b>: intended to be called only by the framework.
     *
     * <p>
     * Implementation notes: copied over from the Isis transaction when the command is persisted.
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.TRANSACTION_ID)
    @Property(
            domainEvent = TransactionIdDomainEvent.class
    )
    @PropertyLayout(
            typicalLength = JdoColumnLength.TRANSACTION_ID
    )
    @Getter @Setter
    private UUID transactionId;

    //endregion

    //region > targetClass (property)

    public static class TargetClassDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.TARGET_CLASS)
    @Property(
            domainEvent = TargetClassDomainEvent.class
    )
    @PropertyLayout(
            named="Class",
            typicalLength = 30
    )
    @Getter
    private String targetClass;

    public void setTargetClass(final String targetClass) {
        this.targetClass = Util.abbreviated(targetClass, JdoColumnLength.TARGET_CLASS);
    }

    //endregion

    //region > targetAction (property)

    public static class TargetActionDomainEvent extends PropertyDomainEvent<String> { }


    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.TARGET_ACTION)
    @Property(
            domainEvent = TargetActionDomainEvent.class,
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            hidden = Where.NOWHERE,
            typicalLength = 30,
            named = "Action"
    )
    @Getter
    private String targetAction;

    public void setTargetAction(final String targetAction) {
        this.targetAction = Util.abbreviated(targetAction, JdoColumnLength.TARGET_ACTION);
    }
    //endregion

    //region > target (property), openTargetObject (action)

    public static class TargetStrDomainEvent extends PropertyDomainEvent<String> { }


    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.BOOKMARK, name="target")
    @Property(
            domainEvent = TargetStrDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.REFERENCES_PARENT,
            named = "Object"
    )
    @Getter @Setter
    private String targetStr;

    //endregion

    //region > arguments (property)

    public static class ArgumentsDomainEvent extends PropertyDomainEvent<String> { }


    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB", sqlType="LONGVARCHAR")
    @Property(
            domainEvent = ArgumentsDomainEvent.class
    )
    @PropertyLayout(
            multiLine = 7,
            hidden = Where.ALL_TABLES
    )
    @Getter @Setter
    private String arguments;

    //endregion

    //region > memberIdentifier (property)

    public static class MemberIdentifierDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.MEMBER_IDENTIFIER)
    @Property(
            domainEvent = MemberIdentifierDomainEvent.class
    )
    @PropertyLayout(
            typicalLength = 60,
            hidden = Where.ALL_TABLES
    )
    @Getter
    private String memberIdentifier;

    public void setMemberIdentifier(final String memberIdentifier) {
        this.memberIdentifier = Util.abbreviated(memberIdentifier, JdoColumnLength.MEMBER_IDENTIFIER);
    }
    //endregion

    //region > memento (property)

    public static class MementoDomainEvent extends PropertyDomainEvent<String> { }


    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = MementoDomainEvent.class
    )
    @PropertyLayout(
            multiLine = 9,
            hidden = Where.ALL_TABLES
    )
    @Getter @Setter
    private String memento;

    //endregion

    //region > asDto (CommandWithDto api programmatic)

    // locally cached
    private transient CommandDto commandDto;

    @Override
    public CommandDto asDto() {
        if(commandDto == null) {
            this.commandDto = buildCommandDto();
        }
        return this.commandDto;
    }

    private CommandDto buildCommandDto() {
        if(getMemento() == null) {
            return null;
        }

        return jaxbService.fromXml(CommandDto.class, getMemento());
    }

    //endregion




    //region > startedAt (property)

    public static class StartedAtDomainEvent extends PropertyDomainEvent<Timestamp> { }

    /**
     * setter is NOT API: intended to be called only by the framework.
     */
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            domainEvent = StartedAtDomainEvent.class
    )
    @Getter @Setter
    private Timestamp startedAt;

    //endregion

    //region > completedAt (property)

    public static class CompletedAtDomainEvent extends PropertyDomainEvent<Timestamp> { }

    /**
     * The date/time at which this interaction completed.
     */
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            domainEvent = CompletedAtDomainEvent.class
    )
    @Getter @Setter
    private Timestamp completedAt;

    //endregion

    //region > duration (derived property)

    public static class DurationDomainEvent extends PropertyDomainEvent<BigDecimal> { }

    /**
     * The number of seconds (to 3 decimal places) that this interaction lasted.
     * 
     * <p>
     * Populated only if it has {@link #getCompletedAt() completed}.
     */
    @javax.validation.constraints.Digits(integer=5, fraction=3)
    @Property(
            domainEvent = DurationDomainEvent.class
    )
    @PropertyLayout(
            named = "Duration"
    )
    public BigDecimal getDuration() {
        return Util.durationBetween(getStartedAt(), getCompletedAt());
    }

    //endregion

    //region > isComplete (derived property)

    public static class IsCompleteDomainEvent extends PropertyDomainEvent<Boolean> { }

    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = IsCompleteDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.OBJECT_FORMS
    )
    public boolean isComplete() {
        return getCompletedAt() != null;
    }
    //endregion

    //region > resultSummary (derived property)

    public static class ResultSummaryDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = ResultSummaryDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.OBJECT_FORMS,
            named = "Result"
    )
    public String getResultSummary() {
        if(getCompletedAt() == null) {
            return "";
        }
        if(getException() != null) {
            return "EXCEPTION";
        } 
        if(getResultStr() != null) {
            return "OK";
        } else {
            return "OK (VOID)";
        }
    }

    //endregion

    //region > result (property), openResultObject (action)

    @Programmatic
    @Override
    public Bookmark getResult() {
        return Util.bookmarkFor(getResultStr());
    }

    @Programmatic
    @Override
    public void setResult(final Bookmark result) {
        setResultStr(Util.asString(result));
    }

    // //////////////////////////////////////


    public static class ResultStrDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.BOOKMARK, name="result")
    @Property(
            domainEvent = ResultStrDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            named = "Result Bookmark"
    )
    @Getter @Setter
    private String resultStr;

    // //////////////////////////////////////

    public static class OpenResultObjectDomainEvent extends ActionDomainEvent { }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = OpenResultObjectDomainEvent.class
    )
    @ActionLayout(
            named = "Open"
    )
    @MemberOrder(name="ResultStr", sequence="1")
    public Object openResultObject() {
        return Util.lookupBookmark(getResult(), bookmarkService, container);
    }
    public boolean hideOpenResultObject() {
        return getResult() == null;
    }

    //endregion

    //region > exception (property), causedException (derived property)

    public static class ExceptionDomainEvent extends PropertyDomainEvent<String> { }

    /**
     * Stack trace of any exception that might have occurred if this interaction/transaction aborted.
     * 
     * <p>
     * Not part of the applib API, because the default implementation is not persistent
     * and so there's no object that can be accessed to be annotated.
     */
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = ExceptionDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            multiLine = 5,
            named = "Exception (if any)"
    )
    @Getter @Setter
    private String exception;


    public static class IsCausedExceptionDomainEvent extends PropertyDomainEvent<Boolean> { }

    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = IsCausedExceptionDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.OBJECT_FORMS
    )
    public boolean isCausedException() {
        return getException() != null;
    }


    //endregion

    //region > ActionInteractionEvent (Command2 impl)

    @Deprecated
    @Programmatic
    @Override
    public ActionInteractionEvent<?> peekActionInteractionEvent() {
        final org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> actionDomainEvent = peekActionDomainEvent();
        if (actionDomainEvent != null && !(actionDomainEvent instanceof ActionInteractionEvent)) {
            throw new IllegalStateException("Most recently pushed event was not an instance of ActionInteractionEvent; use either ActionDomainEvent or the (deprecated) ActionInteractionEvent consistently");
        }
        return (ActionInteractionEvent<?>) actionDomainEvent;
    }

    @Deprecated
    @Programmatic
    @Override
    public void pushActionInteractionEvent(final ActionInteractionEvent<?> event) {
        pushActionDomainEvent(event);
    }

    @Deprecated
    @Programmatic
    @Override
    public ActionInteractionEvent popActionInteractionEvent() {
        final org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> actionDomainEvent = popActionDomainEvent();
        if (actionDomainEvent != null  && !(actionDomainEvent instanceof ActionInteractionEvent)) {
            throw new IllegalStateException("Most recently pushed event was not an instance of ActionInteractionEvent; use either ActionDomainEvent or the (deprecated) ActionInteractionEvent consistently");
        }
        return (ActionInteractionEvent<?>) actionDomainEvent;
    }

    @Deprecated
    @Programmatic
    @Override
    public List<ActionInteractionEvent<?>> flushActionInteractionEvents() {
        final List<org.apache.isis.applib.services.eventbus.ActionDomainEvent<?>> actionDomainEvents = flushActionDomainEvents();
        for (final org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> actionDomainEvent : actionDomainEvents) {
            if (!(actionDomainEvent instanceof ActionInteractionEvent)) {
                throw new IllegalStateException("List of events includes at least one event that is not an instance of ActionInteractionEvent; use either ActionDomainEvent or the (deprecated) ActionInteractionEvent consistently");
            }
        }
        return (List)actionDomainEvents;
    }

    //endregion

    //region > ActionDomainEvent (Command3 impl)

    private final LinkedList<org.apache.isis.applib.services.eventbus.ActionDomainEvent<?>> actionDomainEvents = Lists.newLinkedList();

    @Programmatic
    @Override
    public org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> peekActionDomainEvent() {
        return actionDomainEvents.isEmpty()? null: actionDomainEvents.getLast();
    }

    @Programmatic
    @Override
    public void pushActionDomainEvent(final org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> event) {
        if(peekActionDomainEvent() == event) {
            return;
        }
        this.actionDomainEvents.add(event);
    }

    @Programmatic
    @Override
    public org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> popActionDomainEvent() {
        return !actionDomainEvents.isEmpty() ? actionDomainEvents.removeLast() : null;
    }

    @Programmatic
    @Override
    public List<org.apache.isis.applib.services.eventbus.ActionDomainEvent<?>> flushActionDomainEvents() {
        final List<org.apache.isis.applib.services.eventbus.ActionDomainEvent<?>> events =
                Collections.unmodifiableList(Lists.newArrayList(actionDomainEvents));
        actionDomainEvents.clear();
        return events;
    }

    //endregion

    //region > next(...) impl


    private final Map<String, AtomicInteger> sequenceByName = Maps.newHashMap();

    @Programmatic
    @Override
    public int next(final String sequenceAbbr) {
        AtomicInteger next = sequenceByName.get(sequenceAbbr);
        if(next == null) {
            next = new AtomicInteger(0);
            sequenceByName.put(sequenceAbbr, next);
        } else {
            next.incrementAndGet();
        }
        return next.get();
    }

    //endregion

    //region > persistence (programmatic)


    private Persistence persistence;
    
    @javax.jdo.annotations.NotPersistent
    @Programmatic
    @Override
    public Persistence getPersistence() {
        return persistence;
    }

    @Override
    public void setPersistence(final Persistence persistence) {
        this.persistence = persistence;
    }

    //endregion

    //region > setPersistHint (SPI impl)


    private boolean persistHint;

    @NotPersistent
    @Programmatic
    public boolean isPersistHint() {
        return persistHint;
    }

    @Programmatic
    @Override
    public void setPersistHint(final boolean persistHint) {
        this.persistHint = persistHint;
    }

    //endregion

    //region > helpers, toString

    @Programmatic
    boolean shouldPersist() {
        if(Persistence.PERSISTED == getPersistence()) {
            return true;
        }
        if(Persistence.IF_HINTED == getPersistence()) {
            return isPersistHint();
        }
        return false;
    }


    @Override
    public String toString() {
        return ObjectContracts.toString(this, "targetStr","memberIdentifier","user","startedAt","completedAt","duration","transactionId");
    }

    private final static Ordering<CommandJdo> COMPARATOR = Ordering.natural().onResultOf(CommandJdo::getTimestamp);

    @Override
    public int compareTo(final CommandJdo o) {
        return COMPARATOR.compare(this, o);
    }


    //endregion

    //region > dependencies
    @javax.inject.Inject
    BookmarkService bookmarkService;

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    JaxbService jaxbService;
    //endregion

}
