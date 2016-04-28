/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.command.dom;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberGroupLayout;
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
import org.apache.isis.applib.services.eventbus.ActionInteractionEvent;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;
import org.apache.isis.objectstore.jdo.applib.service.Util;

import org.isisaddons.module.command.CommandModule;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.APPLICATION,
        schema = "isiscommand",
        table="Command")
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="findByTransactionId", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE transactionId == :transactionId "),
    @javax.jdo.annotations.Query(
            name="findBackgroundCommandByTransactionId", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE transactionId == :transactionId "
                    + "&& executeIn == 'BACKGROUND'"),
    @javax.jdo.annotations.Query(
            name="findBackgroundCommandsByParent", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE parent == :parent "
                    + "&& executeIn == 'BACKGROUND'"),
    @javax.jdo.annotations.Query(
            name="findBackgroundCommandsNotYetStarted", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE executeIn == 'BACKGROUND' "
                    + "&& startedAt == null "
                    + "ORDER BY timestamp ASC "
                    ),
    @javax.jdo.annotations.Query(
            name="findCurrent", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE completedAt == null "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findCompleted", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE completedAt != null "
                    + "&& executeIn == 'FOREGROUND' "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTarget", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE targetStr == :targetStr " 
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE timestamp >= :from " 
                    + "&&    timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE timestamp >= :from "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="find", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findRecentByUser", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.command.dom.CommandJdo "
                    + "WHERE user == :user "
                    + "ORDER BY timestamp DESC "
                    + "RANGE 0,10")
})
@DomainObject(
        objectType = "isiscommand.Command",
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        named = "Command"
)
@MemberGroupLayout(
        columnSpans={6,0,6,12}, 
        left={"Identifiers","Target","Notes", "Metadata"},
        right={"Detail","Timings","Results"})
public class CommandJdo extends DomainChangeJdoAbstract implements Command3, HasUsername {

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
        final TitleBuffer buf = new TitleBuffer();
        buf.append(getTargetStr());
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
    @MemberOrder(name="Identifiers", sequence = "10")
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
    @MemberOrder(name="Identifiers", sequence = "20")
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
    @MemberOrder(name="Identifiers", sequence = "32")
    private ExecuteIn executeIn;

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
    @MemberOrder(name="Identifiers",sequence = "40")
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
    @MemberOrder(name="Identifiers",sequence = "50")
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
    @MemberOrder(name="Target", sequence = "10")
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
    @MemberOrder(name="Target", sequence = "20")
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
    @MemberOrder(name="Target", sequence="30")
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
    @MemberOrder(name="Target",sequence = "40")
    private String arguments;

    //endregion

    //region > metadata region dummy property

    public static class MetadataRegionDummyPropertyDomainEvent extends PropertyDomainEvent<String> { }

    /**
     * Exists just that the Wicket viewer will render an (almost) empty metadata region (on which the
     * framework contributed mixin actions will be attached).  The field itself can optionally be hidden
     * using CSS.
     */
    @NotPersistent
    @Property(domainEvent = MetadataRegionDummyPropertyDomainEvent.class, notPersisted = true)
    @PropertyLayout(labelPosition = LabelPosition.NONE, hidden = Where.ALL_TABLES)
    @MemberOrder(name="Metadata", sequence = "1")
    public String getMetadataRegionDummyProperty() {
        return null;
    }
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
    @MemberOrder(name="Detail",sequence = "1")
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
    @MemberOrder(name="Detail",sequence = "30")
    private String memento;

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
    @MemberOrder(name="Timings", sequence = "3")
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
    @MemberOrder(name="Timings", sequence = "4")
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
    @MemberOrder(name="Timings", sequence = "7")
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
    @MemberOrder(name="Timings", sequence = "8")
    public boolean isComplete() {
        return getCompletedAt() != null;
    }
    //endregion

    //region > resultSummary (derived property)

    public static class ResultSummaryDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.NotPersistent
    @MemberOrder(name="Results",sequence = "10")
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
    @MemberOrder(name="Results", sequence="25")
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

    //region > exception (property), causedException (derived property), showException (associated action)

    private String exception;

    /**
     * Stack trace of any exception that might have occurred if this interaction/transaction aborted.
     * 
     * <p>
     * Not visible in the UI, but accessible 
     * <p>
     * Not part of the applib API, because the default implementation is not persistent
     * and so there's no object that can be accessed to be annotated.
     */
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Programmatic
    @Override
    public String getException() {
        return exception;
    }

    @Override
    public void setException(final String exception) {
        this.exception = exception;
    }
    
    
    // //////////////////////////////////////

    public static class IsCausedExceptionDomainEvent extends PropertyDomainEvent<Boolean> { }

    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = IsCausedExceptionDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES
    )
    @MemberOrder(name="Results",sequence = "30")
    public boolean isCausedException() {
        return getException() != null;
    }

    
    // //////////////////////////////////////

    public static class ShowExceptionDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = ShowExceptionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(name="causedException", sequence = "1")
    public String showException() {
        return getException();
    }
    public boolean hideShowException() {
        return !isCausedException();
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

    //endregion

    //region > dependencies
    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private DomainObjectContainer container;
    //endregion

}
