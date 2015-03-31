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
import org.isisaddons.module.command.CommandModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Command.ExecuteIn;
import org.apache.isis.applib.annotation.Command.Persistence;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
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
        left={"Identifiers","Target","Notes"},
        right={"Detail","Timings","Results"})
public class CommandJdo extends DomainChangeJdoAbstract implements Command3, HasUsername {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(CommandJdo.class);

    // //////////////////////////////////////

    public static abstract class PropertyDomainEvent<T> extends CommandModule.PropertyDomainEvent<CommandJdo, T> {
        public PropertyDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final CommandJdo source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommandModule.CollectionDomainEvent<CommandJdo, T> {
        public CollectionDomainEvent(final CommandJdo source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final CommandJdo source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandJdo> {
        public ActionDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final CommandJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final CommandJdo source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public CommandJdo() {
        super(ChangeType.COMMAND);
    }

    // //////////////////////////////////////
    // Identification
    // //////////////////////////////////////

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(getTargetStr());
        buf.append(" ").append(getMemberIdentifier());
        return buf.toString();
    }

    // //////////////////////////////////////
    // user (property)
    // //////////////////////////////////////

    public static class UserDomainEvent extends PropertyDomainEvent<String> {
        public UserDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public UserDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String user;

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.USER_NAME)
    @Property(
            domainEvent = UserDomainEvent.class
    )
    @MemberOrder(name="Identifiers", sequence = "10")
    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }


    @Programmatic
    public String getUsername() {
        return getUser();
    }


    // //////////////////////////////////////
    // timestamp (property)
    // //////////////////////////////////////

    public static class TimestampDomainEvent extends PropertyDomainEvent<Timestamp> {
        public TimestampDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public TimestampDomainEvent(final CommandJdo source, final Identifier identifier, final Timestamp oldValue, final Timestamp newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Timestamp timestamp;

    /**
     * The date/time at which this action was created.
     */
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            domainEvent = TimestampDomainEvent.class
    )
    @MemberOrder(name="Identifiers", sequence = "20")
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    /**
     * <b>NOT API</b>: intended to be called only by the framework.
     */
    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    
    // //////////////////////////////////////
    // executor (property)
    // //////////////////////////////////////
    
    private Executor executor;
    
    @Programmatic
    @javax.jdo.annotations.NotPersistent
    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(final Executor nature) {
        this.executor = nature;
    }

    // //////////////////////////////////////
    // executeIn (property)
    // //////////////////////////////////////

    public static class ExecuteInDomainEvent extends PropertyDomainEvent<ExecuteIn> {
        public ExecuteInDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public ExecuteInDomainEvent(final CommandJdo source, final Identifier identifier, final ExecuteIn oldValue, final ExecuteIn newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private ExecuteIn executeIn;

    /**
     * Whether the action was invoked explicitly by the user, or scheduled as a background
     * task, or as for some other reason, eg a side-effect of rendering an object due to 
     * get-after-post).
     */
    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.Command.EXECUTE_IN)
    @Property(
            domainEvent = ExecuteInDomainEvent.class
    )
    @MemberOrder(name="Identifiers", sequence = "32")
    @Override
    public ExecuteIn getExecuteIn() {
        return executeIn;
    }
    
    /**
     * <b>NOT API</b>: intended to be called only by the framework.
     */
    @Override
    public void setExecuteIn(final ExecuteIn executeIn) {
        this.executeIn = executeIn;
    }


    // //////////////////////////////////////
    // parent (property)
    // //////////////////////////////////////

    public static class ParentDomainEvent extends PropertyDomainEvent<Command> {
        public ParentDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public ParentDomainEvent(final CommandJdo source, final Identifier identifier, final Command oldValue, final Command newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Command parent;
    
    @Override
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(name="parentTransactionId", allowsNull="true")
    @Property(
            domainEvent = ParentDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.PARENTED_TABLES
    )
    @MemberOrder(name="Identifiers",sequence = "40")
    public Command getParent() {
        return parent;
    }

    @Override
    public void setParent(final Command parent) {
        this.parent = parent;
    }

    
    // //////////////////////////////////////
    // transactionId (property)
    // //////////////////////////////////////

    public static class TransactionIdDomainEvent extends PropertyDomainEvent<UUID> {
        public TransactionIdDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }

        public TransactionIdDomainEvent(final CommandJdo source, final Identifier identifier, final UUID oldValue, final UUID newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }



    /**
     * The unique identifier (a GUID) of the transaction in which this command occurred.
     */
    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = TransactionIdDomainEvent.class
    )
    @PropertyLayout(
            typicalLength = JdoColumnLength.TRANSACTION_ID
    )
    @MemberOrder(name="Identifiers",sequence = "50")
    @Override
    public UUID getTransactionId() {
        return getTransactionUuid() != null? UUID.fromString(getTransactionUuid()): null;
    }

    /**
     * <b>NOT API</b>: intended to be called only by the framework.
     * 
     * <p>
     * Implementation notes: copied over from the Isis transaction when the command is persisted.
     */
    @Override
    public void setTransactionId(final UUID transactionId) {
        setTransactionUuid(transactionId != null ? transactionId.toString(): null);
    }

    // //////////////////////////////////////

    private String transactionUuid;

    /**
     * The unique identifier (a GUID) of the transaction in which this command occurred.
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(allowsNull="false", name="transactionId", length=JdoColumnLength.TRANSACTION_ID)
    @Programmatic
    public String getTransactionUuid() {
        return transactionUuid;
    }

    /**
     * <b>NOT API</b>: intended to be called only by the framework.
     *
     * <p>
     * Implementation notes: copied over from the Isis transaction when the command is persisted.
     */
    public void setTransactionUuid(final String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }


    // //////////////////////////////////////
    // targetClass (property)
    // //////////////////////////////////////

    public static class TargetClassDomainEvent extends PropertyDomainEvent<String> {
        public TargetClassDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public TargetClassDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String targetClass;

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.TARGET_CLASS)
    @Property(
            domainEvent = TargetClassDomainEvent.class
    )
    @PropertyLayout(
            named="Class",
            typicalLength = 30
    )
    @MemberOrder(name="Target", sequence = "10")
    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(final String targetClass) {
        this.targetClass = Util.abbreviated(targetClass, JdoColumnLength.TARGET_CLASS);
    }


    // //////////////////////////////////////
    // targetAction (property)
    // //////////////////////////////////////

    public static class TargetActionDomainEvent extends PropertyDomainEvent<String> {
        public TargetActionDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public TargetActionDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String targetAction;
    
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
    @MemberOrder(name="Target", sequence = "20")
    public String getTargetAction() {
        return targetAction;
    }
    
    public void setTargetAction(final String targetAction) {
        this.targetAction = Util.abbreviated(targetAction, JdoColumnLength.TARGET_ACTION);
    }
    

    // //////////////////////////////////////
    // target (property)
    // openTargetObject (action)
    // //////////////////////////////////////

    public static class TargetStrDomainEvent extends PropertyDomainEvent<String> {
        public TargetStrDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public TargetStrDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String targetStr;

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.BOOKMARK, name="target")
    @Property(
            domainEvent = TargetStrDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            named = "Object"
    )
    @MemberOrder(name="Target", sequence="30")
    public String getTargetStr() {
        return targetStr;
    }

    public void setTargetStr(final String targetStr) {
        this.targetStr = targetStr;
    }

    // //////////////////////////////////////
    // arguments (property)
    // //////////////////////////////////////

    public static class ArgumentsDomainEvent extends PropertyDomainEvent<String> {
        public ArgumentsDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public ArgumentsDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String arguments;
    
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = ArgumentsDomainEvent.class
    )
    @PropertyLayout(
            multiLine = 7,
            hidden = Where.ALL_TABLES
    )
    @MemberOrder(name="Target",sequence = "40")
    public String getArguments() {
        return arguments;
    }
    
    public void setArguments(final String arguments) {
        this.arguments = arguments;
    }

    

    // //////////////////////////////////////
    // memberIdentifier (property)
    // //////////////////////////////////////

    public static class MemberIdentifierDomainEvent extends PropertyDomainEvent<String> {
        public MemberIdentifierDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public MemberIdentifierDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String memberIdentifier;
    
    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.MEMBER_IDENTIFIER)
    @Property(
            domainEvent = MemberIdentifierDomainEvent.class
    )
    @PropertyLayout(
            typicalLength = 60,
            hidden = Where.ALL_TABLES
    )
    @MemberOrder(name="Detail",sequence = "1")
    public String getMemberIdentifier() {
        return memberIdentifier;
    }

    public void setMemberIdentifier(final String memberIdentifier) {
        this.memberIdentifier = Util.abbreviated(memberIdentifier, JdoColumnLength.MEMBER_IDENTIFIER);
    }


    // //////////////////////////////////////
    // memento (property)
    // //////////////////////////////////////

    public static class MementoDomainEvent extends PropertyDomainEvent<String> {
        public MementoDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public MementoDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String memento;
    
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = MementoDomainEvent.class
    )
    @PropertyLayout(
            multiLine = 9,
            hidden = Where.ALL_TABLES
    )
    @MemberOrder(name="Detail",sequence = "30")
    public String getMemento() {
        return memento;
    }
    
    public void setMemento(final String memento) {
        this.memento = memento;
    }



    // //////////////////////////////////////
    // startedAt (property)
    // //////////////////////////////////////

    public static class StartedAtDomainEvent extends PropertyDomainEvent<Timestamp> {
        public StartedAtDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public StartedAtDomainEvent(final CommandJdo source, final Identifier identifier, final Timestamp oldValue, final Timestamp newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Timestamp startedAt;

    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            domainEvent = StartedAtDomainEvent.class
    )
    @MemberOrder(name="Timings", sequence = "3")
    public Timestamp getStartedAt() {
        return startedAt;
    }

    /**
     * <b>NOT API</b>: intended to be called only by the framework.
     */
    public void setStartedAt(final Timestamp startedAt) {
        this.startedAt = startedAt;
    }
    
    
    
    // //////////////////////////////////////
    // completedAt (property)
    // //////////////////////////////////////

    public static class CompletedAtDomainEvent extends PropertyDomainEvent<Timestamp> {
        public CompletedAtDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public CompletedAtDomainEvent(final CommandJdo source, final Identifier identifier, final Timestamp oldValue, final Timestamp newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Timestamp completedAt;

    /**
     * The date/time at which this interaction completed.
     */
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            domainEvent = CompletedAtDomainEvent.class
    )
    @MemberOrder(name="Timings", sequence = "4")
    @Override
    public Timestamp getCompletedAt() {
        return completedAt;
    }

    @Override
    public void setCompletedAt(final Timestamp completed) {
        this.completedAt = completed;
    }


    // //////////////////////////////////////
    // duration (derived property)
    // //////////////////////////////////////

    public static class DurationDomainEvent extends PropertyDomainEvent<BigDecimal> {
        public DurationDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public DurationDomainEvent(final CommandJdo source, final Identifier identifier, final BigDecimal oldValue, final BigDecimal newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

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



    // //////////////////////////////////////
    // complete (derived property)
    // //////////////////////////////////////

    public static class IsCompleteDomainEvent extends PropertyDomainEvent<Boolean> {
        public IsCompleteDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public IsCompleteDomainEvent(final CommandJdo source, final Identifier identifier, final Boolean oldValue, final Boolean newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

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


    // //////////////////////////////////////
    // resultSummary (derived property)
    // //////////////////////////////////////

    public static class ResultSummaryDomainEvent extends PropertyDomainEvent<String> {
        public ResultSummaryDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public ResultSummaryDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

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

    
    // //////////////////////////////////////
    // result (property)
    // openResultObject (action)
    // //////////////////////////////////////
    
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

    public static class ResultStrDomainEvent extends PropertyDomainEvent<String> {
        public ResultStrDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public ResultStrDomainEvent(final CommandJdo source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String resultStr;

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.BOOKMARK, name="result")
    @Property(
            domainEvent = ResultStrDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            named = "Result Bookmark"
    )
    @MemberOrder(name="Results", sequence="25")
    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(final String resultStr) {
        this.resultStr = resultStr;
    }

    // //////////////////////////////////////

    public static class OpenResultObjectDomainEvent extends ActionDomainEvent {
        public OpenResultObjectDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public OpenResultObjectDomainEvent(final CommandJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
        public OpenResultObjectDomainEvent(final CommandJdo source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

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


    // //////////////////////////////////////
    // exception (property), causedException (derived property), showException (associated action)
    // //////////////////////////////////////

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

    public static class IsCausedExceptionDomainEvent extends PropertyDomainEvent<Boolean> {
        public IsCausedExceptionDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public IsCausedExceptionDomainEvent(final CommandJdo source, final Identifier identifier, final Boolean oldValue, final Boolean newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

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

    public static class ShowExceptionDomainEvent extends ActionDomainEvent {
        public ShowExceptionDomainEvent(final CommandJdo source, final Identifier identifier) {
            super(source, identifier);
        }
        public ShowExceptionDomainEvent(final CommandJdo source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
        public ShowExceptionDomainEvent(final CommandJdo source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

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


    // //////////////////////////////////////
    // ActionInteractionEvent (Command2 impl)
    // //////////////////////////////////////

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


    // //////////////////////////////////////
    // ActionDomainEvent (Command3 impl)
    // //////////////////////////////////////

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


    // //////////////////////////////////////
    // next(...) impl
    // //////////////////////////////////////

    private final Map<String, AtomicInteger> sequenceByName = Maps.newHashMap();

    @Programmatic
    @Override
    public int next(final String sequenceName) {
        AtomicInteger next = sequenceByName.get(sequenceName);
        if(next == null) {
            next = new AtomicInteger(0);
            sequenceByName.put(sequenceName, next);
        } else {
            next.incrementAndGet();
        }
        return next.get();
    }

    
    // //////////////////////////////////////
    // persistence (programmatic)
    // //////////////////////////////////////

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


    // //////////////////////////////////////
    // setPersistHint (SPI impl)
    // //////////////////////////////////////
    
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

    // //////////////////////////////////////
    
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



    // //////////////////////////////////////
    // toString
    // //////////////////////////////////////

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "targetStr,memberIdentifier,user,startedAt,completedAt,duration,transactionId");
    }

    // //////////////////////////////////////
    // dependencies
    // //////////////////////////////////////
    

    @javax.inject.Inject
    private BookmarkService bookmarkService;
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
