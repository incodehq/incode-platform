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
package org.isisaddons.module.publishmq.dom.jdo.status;

import java.sql.Timestamp;
import java.util.UUID;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.BookmarkService;
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
        table="StatusMessage",
        objectIdClass=StatusMessagePK.class)
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="findByTransactionId", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage "
                    + "WHERE transactionId == :transactionId "
                    + "ORDER BY timestamp DESC DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage "
                    + "WHERE timestamp >= :from " 
                    + "&&    timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage "
                    + "WHERE timestamp >= :from "
                    + "ORDER BY timestamp DESC, transactionId DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage "
                    + "WHERE timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC"),
    @javax.jdo.annotations.Query(
            name="find", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage "
                    + "ORDER BY timestamp DESC, transactionId DESC")
})
@MemberGroupLayout(
        columnSpans={6,0,6,12},
        left={"Identifiers","Target","Metadata"},
        right={"Detail","State"})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "isispublishmq.StatusMessage"
)
@DomainObjectLayout(
        named = "Status Message"
)
public class StatusMessage extends DomainChangeJdoAbstract implements HasTransactionId {

    //region > domain events
    public static abstract class PropertyDomainEvent<T>
            extends PublishMqModule.PropertyDomainEvent<StatusMessage, T> {
    }

    public static abstract class CollectionDomainEvent<T>
            extends PublishMqModule.CollectionDomainEvent<StatusMessage, T> {
    }

    public static abstract class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<StatusMessage> {
    }
    //endregion

    //region > constructor, identification

    public StatusMessage() {
        super(ChangeType.PUBLISHED_EVENT);
    }

    public String title() {
        return Util.abbreviated(getMessage(), 100);
    }
    //endregion


    //region > timestamp (property)

    public static class TimestampDomainEvent extends PropertyDomainEvent<Timestamp> { }

    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            domainEvent = TimestampDomainEvent.class
    )
    @Getter @Setter
    @MemberOrder(name="Identifiers", sequence = "20")
    private Timestamp timestamp;
    //endregion

    //region > transactionId (property)

    public static class TransactionIdDomainEvent extends PropertyDomainEvent<UUID> {
    }

    /**
     * The unique identifier (a GUID) of the transaction in which this published event was persisted.
     *
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getTimestamp()} timestamp}) makes up the
     * {@link StatusMessagePK primary key}.
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

    //region > targetStrDomain (hidden property, required by superclass, always null)

    @javax.jdo.annotations.NotPersistent
    @Property(
            hidden = Where.EVERYWHERE
    )
    @Getter @Setter
    private String targetStr;

    //endregion

    //region > message (property)

    public static class MessageDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.StatusMessage.MESSAGE)
    @Property(
            domainEvent = MessageDomainEvent.class
    )
    @PropertyLayout(
            typicalLength = 60
    )
    @MemberOrder(name="Detail",sequence = "20")
    @Getter
    private String message;

    public void setMessage(final String message) {
        this.message = Util.abbreviated(message, JdoColumnLength.StatusMessage.MESSAGE);
    }

    //endregion

    //region > detail (property)

    public static class DetailDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB", sqlType="LONGVARCHAR")
    @Property(
            domainEvent = DetailDomainEvent.class
    )
    @Getter @Setter
    @MemberOrder(name="Detail", sequence = "40")
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            multiLine = 14
    )
    private String detail;

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
        return ObjectContracts.toString(this, "timestamp","transactionId","message");
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private RepositoryService repositoryService;

    //endregion

}
