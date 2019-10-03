package org.isisaddons.module.publishmq.dom.status.impl;

import java.sql.Timestamp;
import java.util.UUID;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.RepresentsInteractionMemberExecution;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;
import org.apache.isis.objectstore.jdo.applib.service.Util;
import org.isisaddons.module.publishmq.PublishMqModule;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.APPLICATION,
        schema = "isispublishmq",
        table="StatusMessage",
        objectIdClass= StatusMessagePK.class)
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="findByTransactionId", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.status.impl.StatusMessage "
                    + "WHERE transactionId == :transactionId "
                    + "ORDER BY timestamp DESC, sequence ASC"),
    @javax.jdo.annotations.Query(
            name="findByTransactionIdAndSequence", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.status.impl.StatusMessage "
                    + "WHERE transactionId == :transactionId "
                    + "&&    sequence      == :sequence "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTransactionIds", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.status.impl.StatusMessage "
                    + "WHERE :transactionIds.contains(transactionId) "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence ASC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBetween", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.status.impl.StatusMessage "
                    + "WHERE timestamp >= :from "
                    + "&&    timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence ASC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampAfter", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.status.impl.StatusMessage "
                    + "WHERE timestamp >= :from "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence ASC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBefore", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.status.impl.StatusMessage "
                    + "WHERE timestamp <= :to "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence ASC"),
    @javax.jdo.annotations.Query(
            name="find", language="JDOQL",
            value="SELECT "
                    + "FROM org.isisaddons.module.publishmq.dom.status.impl.StatusMessage "
                    + "ORDER BY timestamp DESC, transactionId DESC, sequence ASC")
})
@javax.jdo.annotations.Indices( {
        @Index(
                name = "StatusMessage_transactionId_sequence_IDX",
                members = { "transactionId", "sequence" }
        )
})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "isispublishmq.StatusMessage"
)
@DomainObjectLayout(
        named = "Status Message"
)
public class StatusMessage implements HasTransactionId, RepresentsInteractionMemberExecution {

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

    //region > identification

    public String title() {
        return Util.abbreviated(getMessage(), 100);
    }
    //endregion

    //region > timestamp (property)

    public static class TimestampDomainEvent extends PropertyDomainEvent<Timestamp> { }

    /**
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getSequence()} and {@link #getTimestamp()} timestamp}) makes up the {@link StatusMessagePK primary key}.
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="false", position = 0)
    @Property(
            domainEvent = TimestampDomainEvent.class
    )
    @Getter @Setter
    private Timestamp timestamp;
    //endregion

    //region > transactionId (property)

    public static class TransactionIdDomainEvent extends PropertyDomainEvent<UUID> {
    }

    /**
     * Along with {@link #getSequence()}, uniquely identifies the <code>PublishedEvent</code> to which this status message relates.
     *
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getSequence()} and {@link #getTimestamp()} timestamp}) makes up the {@link StatusMessagePK priamry key}.
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(allowsNull="false",length=JdoColumnLength.TRANSACTION_ID, position = 1)
    @Property(
            domainEvent = TransactionIdDomainEvent.class
    )
    @PropertyLayout(
            typicalLength = JdoColumnLength.TRANSACTION_ID,
            hidden = Where.PARENTED_TABLES
    )
    @Getter @Setter
    private UUID transactionId;

    //endregion

    //region > sequence (property)

    public static class SequenceDomainEvent extends PropertyDomainEvent<Integer> {
    }

    /**
     * Along with {@link #getTransactionId()}, this is a 0-based additional identifier that uniquely identifies the
     * <code>PublishedEvent</code> to which this status message relates.
     *
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getSequence() sequence}) makes up the
     * primary key.
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(position = 2)
    @Property(
            domainEvent = SequenceDomainEvent.class
    )
    @Getter @Setter
    private int sequence;

    //endregion

    //region > message (property)

    public static class MessageDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.StatusMessage.MESSAGE, position = 4)
    @Property(
            domainEvent = MessageDomainEvent.class
    )
    @PropertyLayout(
            hidden = Where.OBJECT_FORMS,
            typicalLength = 60
    )
    @Getter
    private String message;

    public void setMessage(final String message) {
        this.message = Util.abbreviated(message, JdoColumnLength.StatusMessage.MESSAGE);
    }


    @Property(
            domainEvent = MessageDomainEvent.class
    )
    @PropertyLayout(
            named = "Message",
            hidden = Where.ALL_TABLES,
            typicalLength = 60
    )
    public String getMessageOnForm() {
        return getMessage();
    }


    //endregion

    //region > oid (property)

    public static class OidDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="true", length = JdoColumnLength.BOOKMARK, position = 5)
    @Property(
            domainEvent = OidDomainEvent.class
    )
    @Getter @Setter
    private String oid;

    //endregion

    //region > uri (property)

    public static class UriDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="true", length = JdoColumnLength.StatusMessage.URI, position = 7)
    @Property(
            domainEvent = UriDomainEvent.class
    )
    @Getter @Setter
    private String uri;

    //endregion

    //region > status (property)

    public static class StatusDomainEvent extends PropertyDomainEvent<Integer> {
    }

    @javax.jdo.annotations.Column(allowsNull="true", position = 6)
    @Property(
            domainEvent = StatusDomainEvent.class
    )
    @Getter @Setter
    private Integer status;

    //endregion

    //region > detail (property)

    public static class DetailDomainEvent extends PropertyDomainEvent<String> {
    }

    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB", sqlType="LONGVARCHAR", position = 3)
    @Property(
            domainEvent = DetailDomainEvent.class
    )
    @Getter @Setter
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            multiLine = 14
    )
    private String detail;

    //endregion

    //region > toString

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "timestamp","transactionId","message");
    }
    //endregion

}
