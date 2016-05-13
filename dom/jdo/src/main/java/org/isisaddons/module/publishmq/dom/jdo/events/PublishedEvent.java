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
package org.isisaddons.module.publishmq.dom.jdo.events;

import java.util.UUID;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.HasUsername;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.publish.EventType;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;
import org.apache.isis.objectstore.jdo.applib.service.Util;

import org.isisaddons.module.publishmq.PublishMqModule;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.APPLICATION,
        schema = "isispublishmq",
        table="PublishedEvent",
        objectIdClass=PublishedEventPK.class)
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="findByTransactionId", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE transactionId == :transactionId "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTarget", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr " 
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE timestamp >= :from " 
                    + "&&    timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE timestamp >= :from "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="find", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC"),
    @javax.jdo.annotations.Query(
            name="findRecentByUser", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE user == :user "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC "
                    + "RANGE 0,30"),
    @javax.jdo.annotations.Query(
            name="findRecentByTarget", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent "
                    + "WHERE targetStr == :targetStr "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence DESC "
                    + "RANGE 0,30")
})
@MemberGroupLayout(
        columnSpans={6,0,6,12},
        left={"Identifiers","Target","Metadata"},
        right={"Detail","State"})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "isispublishmq.PublishedEvent"
)
@DomainObjectLayout(
        named = "Published Event"
)
public class PublishedEvent extends DomainChangeJdoAbstract implements HasTransactionId, HasUsername {

    //region > domain events
    public static abstract class PropertyDomainEvent<T>
            extends PublishMqModule.PropertyDomainEvent<PublishedEvent, T> {
    }

    public static abstract class CollectionDomainEvent<T>
            extends PublishMqModule.CollectionDomainEvent<PublishedEvent, T> {
    }

    public static abstract class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<PublishedEvent> {
    }
    //endregion

    //region > constructor, identification

    public PublishedEvent() {
        super(ChangeType.PUBLISHED_INTERACTION);
    }
    //endregion

    //region > title (hidden property)
    public static class TitleDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="false", length=255)
    @Property(hidden = Where.EVERYWHERE)
    @Title
    @Getter @Setter
    private String title;
    //endregion

    //region > user (property), getUsername()
    public static class UserDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="false", length=50)
    @Property(
            domainEvent = UserDomainEvent.class
    )
    @MemberOrder(name="Identifiers", sequence = "10")
    @Getter @Setter
    private String user;

    @Override
    @Programmatic
    public String getUsername() {
        return getUser();
    }
    //endregion

    //region > timestamp (property)

    public static class TimestampDomainEvent extends PropertyDomainEvent<java.sql.Timestamp> { }

    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            domainEvent = TimestampDomainEvent.class
    )
    @Getter @Setter
    @MemberOrder(name="Identifiers", sequence = "20")
    private java.sql.Timestamp timestamp;
    //endregion

    //region > transactionId (property)

    public static class TransactionIdDomainEvent extends PropertyDomainEvent<UUID> {
    }

    /**
     * The unique identifier (a GUID) of the transaction in which this published event was persisted.
     *
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getSequence() sequence}) makes up the
     * primary key.
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(allowsNull="false",length=JdoColumnLength.TRANSACTION_ID)
    @Property(
            domainEvent = TransactionIdDomainEvent.class
    )
    @PropertyLayout(
            typicalLength = JdoColumnLength.TRANSACTION_ID
    )
    @MemberOrder(name="Identifiers", sequence = "30")
    @Getter @Setter
    private UUID transactionId;

    //endregion

    //region > sequence (property)

    public static class SequenceDomainEvent extends PropertyDomainEvent<Integer> {
    }

    /**
     * The 0-based additional identifier of a published event within the given {@link #getTransactionId() transaction}.
     *
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getSequence() sequence}) makes up the
     * primary key.
     */
    @javax.jdo.annotations.PrimaryKey
    @Property(
            domainEvent = SequenceDomainEvent.class
    )
    @MemberOrder(name="Identifiers", sequence = "40")
    @Getter @Setter
    private int sequence;

    //endregion

    //region > eventType (property)

    public static class EventTypeDomainEvent extends PropertyDomainEvent<EventType> {
    }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.PublishedEvent.EVENT_TYPE)
    @Property(
            domainEvent = EventTypeDomainEvent.class
    )
    @MemberOrder(name="Identifiers",sequence = "50")
    @Getter @Setter
    private PublishedEventType eventType;
    //endregion

    //region > targetClass (property)

    public static class TargetClassDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.TARGET_CLASS)
    @Property(
            domainEvent = TargetClassDomainEvent.class
    )
    @PropertyLayout(
            named = "Class",
            typicalLength = 30
    )
    @MemberOrder(name="Target", sequence = "10")
    @Getter
    private String targetClass;

    public void setTargetClass(final String targetClass) {
        this.targetClass = Util.abbreviated(targetClass, JdoColumnLength.TARGET_CLASS);
    }
    //endregion

    //region > targetAction (property)

    public static class TargetActionDomainEvent extends PropertyDomainEvent<String> {
    }

    /**
     * Only populated for {@link EventType#ACTION_INVOCATION}
     */
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.TARGET_ACTION)
    @Property(
            domainEvent = TargetActionDomainEvent.class
    )
    @PropertyLayout(
            named = "Action",
            typicalLength = 30
    )
    @Getter
    @MemberOrder(name="Target", sequence = "20")
    private String targetAction;

    public void setTargetAction(final String targetAction) {
        this.targetAction = Util.abbreviated(targetAction, JdoColumnLength.TARGET_ACTION);
    }
    //endregion

    //region > targetStrDomain (property)

    public static class TargetStrDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.BOOKMARK, name="target")
    @Property(
            domainEvent = TargetStrDomainEvent.class
    )
    @PropertyLayout(
            named = "Object"
    )
    @MemberOrder(name="Target", sequence="30")
    @Getter @Setter
    private String targetStr;

    //endregion

    //region > memberIdentifier (property)

    public static class MemberIdentifierDomainEvent extends PropertyDomainEvent<String> {
    }

    /**
     * Holds a string representation of the invoked action, equivalent to
     * {@link Identifier#toClassAndNameIdentityString()}.
     *
     * <p>
     * Only populated for {@link EventType#ACTION_INVOCATION},
     * returns <tt>null</tt> otherwise.
     *
     * <p>
     * This property is called 'memberIdentifier' rather than 'actionIdentifier' for
     * consistency with other services (such as auditing and publishing) that may act on
     * properties rather than simply just actions.
     */
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.MEMBER_IDENTIFIER)
    @Property(
            domainEvent = MemberIdentifierDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            typicalLength = 60
    )
    @MemberOrder(name="Detail",sequence = "20")
    @Getter
    private String memberIdentifier;
    

    public void setMemberIdentifier(final String actionIdentifier) {
        this.memberIdentifier = Util.abbreviated(actionIdentifier, JdoColumnLength.MEMBER_IDENTIFIER);
    }

    //endregion

    //region > serializedForm (property)

    public static class SerializedFormDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB", sqlType="LONGVARCHAR")
    @Property(
            domainEvent = SerializedFormDomainEvent.class
    )
    @Getter @Setter
    @MemberOrder(name="Detail", sequence = "40")
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            multiLine = 14
    )
    private String serializedForm;

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

    //region > toString

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "timestamp","user","eventType","memberIdentifier");
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private RepositoryService repositoryService;

    //endregion

}
