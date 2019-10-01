package org.isisaddons.module.publishmq.dom.outbox.events;

import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.HasUsername;
import org.apache.isis.applib.services.RepresentsInteractionMemberExecution;
import org.apache.isis.applib.services.publish.EventType;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;
import org.apache.isis.objectstore.jdo.applib.service.Util;
import org.isisaddons.module.publishmq.PublishMqModule;

import lombok.Getter;
import lombok.Setter;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.APPLICATION,
        schema = "isispublishmq",
        table="PublishedEventAbstract",
        objectIdClass= PublishedEventPK.class)
@Inheritance(strategy = InheritanceStrategy.COMPLETE_TABLE)
public abstract class PublishedEventAbstract extends DomainChangeJdoAbstract implements HasTransactionId,
        RepresentsInteractionMemberExecution, HasUsername {

    public static abstract class PropertyDomainEvent<T>
            extends PublishMqModule.PropertyDomainEvent<PublishedEventAbstract, T> { }
    public static abstract class CollectionDomainEvent<T>
            extends PublishMqModule.CollectionDomainEvent<PublishedEventAbstract, T> { }
    public static abstract class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<PublishedEventAbstract> { }

    public PublishedEventAbstract() {
        super(ChangeType.PUBLISHED_INTERACTION);
    }

    public String title() {
        // nb: not thread-safe
        // formats defined in https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        final TitleBuffer buf = new TitleBuffer();
        buf.append(format.format(getTimestamp()));
        buf.append(" ").append(getMemberIdentifier());
        return buf.toString();
    }



    @javax.jdo.annotations.Column(allowsNull="false", length=255, position = 9)
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private String title;



    public static class UserDomainEvent extends PropertyDomainEvent<String> { }
    @javax.jdo.annotations.Column(allowsNull="false", length=50, position = 10)
    @Property(domainEvent = UserDomainEvent.class)
    @Getter @Setter
    private String user;

    @Override
    @Programmatic
    public String getUsername() {
        return getUser();
    }



    public static class TimestampDomainEvent extends PropertyDomainEvent<java.sql.Timestamp> { }
    @javax.jdo.annotations.Persistent
    @javax.jdo.annotations.Column(allowsNull="false", position = 8)
    @Property(domainEvent = TimestampDomainEvent.class)
    @Getter @Setter
    private java.sql.Timestamp timestamp;


    public static class TransactionIdDomainEvent extends PropertyDomainEvent<UUID> { }

    /**
     * The unique identifier (a GUID) of the transaction in which this published event was persisted.
     *
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getSequence() sequence}) makes up the
     * primary key.
     * </p>
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(allowsNull="false",length=JdoColumnLength.TRANSACTION_ID, position = 0)
    @Property(domainEvent = TransactionIdDomainEvent.class)
    @PropertyLayout(typicalLength = JdoColumnLength.TRANSACTION_ID)
    @Getter @Setter
    private UUID transactionId;



    public static class SequenceDomainEvent extends PropertyDomainEvent<Integer> { }
    /**
     * The 0-based additional identifier of a published event within the given {@link #getTransactionId() transaction}.
     *
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getSequence() sequence}) makes up the
     * primary key.
     * </p>
     */
    @javax.jdo.annotations.PrimaryKey
    @javax.jdo.annotations.Column(position = 1)
    @Property(domainEvent = SequenceDomainEvent.class)
    @Getter @Setter
    private int sequence;



    public static class EventTypeDomainEvent extends PropertyDomainEvent<EventType> { }
    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.PublishedEvent.EVENT_TYPE, position = 2)
    @Property(domainEvent = EventTypeDomainEvent.class)
    @Getter @Setter
    private PublishedEventType eventType;



    public static class TargetClassDomainEvent extends PropertyDomainEvent<String> { }
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.TARGET_CLASS, position = 6)
    @Property(domainEvent = TargetClassDomainEvent.class)
    @PropertyLayout(named = "Class", typicalLength = 30)
    @Getter
    private String targetClass;

    public void setTargetClass(final String targetClass) {
        this.targetClass = Util.abbreviated(targetClass, JdoColumnLength.TARGET_CLASS);
    }



    public static class TargetActionDomainEvent extends PropertyDomainEvent<String> { }
    /**
     * Only populated for {@link EventType#ACTION_INVOCATION}
     */
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.TARGET_ACTION, position = 5)
    @Property(domainEvent = TargetActionDomainEvent.class)
    @PropertyLayout(named = "Action", typicalLength = 30)
    @Getter
    private String targetAction;

    public void setTargetAction(final String targetAction) {
        this.targetAction = Util.abbreviated(targetAction, JdoColumnLength.TARGET_ACTION);
    }



    public static class TargetStrDomainEvent extends PropertyDomainEvent<String> { }
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.BOOKMARK, name="target", position = 7)
    @Property(domainEvent = TargetStrDomainEvent.class)
    @PropertyLayout(named = "Object")
    @Getter @Setter
    private String targetStr;



    public static class MemberIdentifierDomainEvent extends PropertyDomainEvent<String> { }
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
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.MEMBER_IDENTIFIER, position = 3)
    @Property(domainEvent = MemberIdentifierDomainEvent.class)
    @PropertyLayout(
            hidden = Where.ALL_TABLES,
            typicalLength = 60
    )
    @Getter
    private String memberIdentifier;

    public void setMemberIdentifier(final String actionIdentifier) {
        this.memberIdentifier = Util.abbreviated(actionIdentifier, JdoColumnLength.MEMBER_IDENTIFIER);
    }



    public static class SerializedFormDomainEvent extends PropertyDomainEvent<String> { }
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB", sqlType="LONGVARCHAR", position = 4)
    @Property(domainEvent = SerializedFormDomainEvent.class)
    @PropertyLayout(hidden = Where.ALL_TABLES, multiLine = 14)
    @Getter @Setter
    private String serializedForm;



    @Override public String toString() {
        return "PublishedEventAbstract{" +
                "timestamp='" + timestamp + '\'' +
                ", user=" + user +
                ", eventType=" + eventType +
                ", memberIdentifier='" + memberIdentifier + '\'' +
                '}';
    }

}
