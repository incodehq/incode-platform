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
package org.isisaddons.module.audit.dom;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.Indices;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;
import org.apache.isis.objectstore.jdo.applib.service.Util;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        table="IsisAuditEntry")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="findByTransactionId", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE transactionId == :transactionId"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp >= :from "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTargetAndTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE targetStr == :targetStr " 
                    + "&& timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTarget", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE targetStr == :targetStr " 
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBetween", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE timestamp >= :from " 
                    + "&&    timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampAfter", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE timestamp >= :from "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="findByTimestampBefore", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "WHERE timestamp <= :to "
                    + "ORDER BY timestamp DESC"),
    @javax.jdo.annotations.Query(
            name="find", language="JDOQL",  
            value="SELECT "
                    + "FROM org.isisaddons.module.audit.dom.AuditEntry "
                    + "ORDER BY timestamp DESC")
})
@Indices({
    @Index(name="IsisAuditEntry_ak", unique="true", 
            columns={
                @javax.jdo.annotations.Column(name="transactionId"),
                @javax.jdo.annotations.Column(name="target"),
                @javax.jdo.annotations.Column(name="propertyId")
                })
})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "IsisAuditEntry"
)
@MemberGroupLayout(
        columnSpans={6,0,6},
        left={"Identifiers","Target"},
        right={"Detail"})
public class AuditEntry extends DomainChangeJdoAbstract implements HasTransactionId {

    public static abstract class AbstractPropertyEvent<T> extends PropertyDomainEvent<AuditEntry, T> {
        public AbstractPropertyEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }

        public AbstractPropertyEvent(final AuditEntry source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    // //////////////////////////////////////

    public AuditEntry() {
        super(ChangeType.AUDIT_ENTRY);
    }

    // //////////////////////////////////////
    // Identification
    // //////////////////////////////////////

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(
        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(getTimestamp()));
        buf.append(",", getUser());
        buf.append(":", getTargetStr());
        buf.append(" ", getMemberIdentifier());
        return buf.toString();
    }
    

    // //////////////////////////////////////
    // user (property)
    // //////////////////////////////////////

    public static class UserEvent extends AbstractPropertyEvent<String> {
        public UserEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public UserEvent(final AuditEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String user;

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.USER_NAME)
    @Property(
            domainEvent = UserEvent.class
    )
    @PropertyLayout(
            hidden = Where.PARENTED_TABLES
    )
    @MemberOrder(name="Identifiers",sequence = "10")
    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }
    

    // //////////////////////////////////////
    // timestamp (property)
    // //////////////////////////////////////

    public static class TimestampEvent extends AbstractPropertyEvent<java.sql.Timestamp> {
        public TimestampEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public TimestampEvent(final AuditEntry source, final Identifier identifier, final Timestamp oldValue, final Timestamp newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Timestamp timestamp;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            domainEvent = TimestampEvent.class
    )
    @PropertyLayout(
            hidden = Where.PARENTED_TABLES
    )
    @MemberOrder(name="Identifiers",sequence = "20")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    // //////////////////////////////////////
    // transactionId (property)
    // //////////////////////////////////////

    public static class TransactionIdEvent extends AbstractPropertyEvent<UUID> {
        public TransactionIdEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }

        public TransactionIdEvent(final AuditEntry source, final Identifier identifier, final UUID oldValue, final UUID newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private UUID transactionId;

    /**
     * The unique identifier (a GUID) of the transaction in which this audit entry was persisted.
     * 
     * <p>
     * The combination of ({@link #getTransactionId() transactionId}, {@link #getTargetStr() target}, {@link #getPropertyId() propertyId} ) makes up the
     * (non-enforced) alternative key.
     */
    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.TRANSACTION_ID)
    @Property(
            domainEvent = TransactionIdEvent.class,
            editing = Editing.DISABLED
    )
    @PropertyLayout(
            hidden=Where.PARENTED_TABLES,
            typicalLength = 36
    )
    @MemberOrder(name="Identifiers",sequence = "30")
    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public void setTransactionId(final UUID transactionId) {
        this.transactionId = transactionId;
    }


    // //////////////////////////////////////
    // targetClass (property)
    // //////////////////////////////////////

    public static class TargetClassEvent extends AbstractPropertyEvent<String> {
        public TargetClassEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public TargetClassEvent(final AuditEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String targetClass;

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.TARGET_CLASS)
    @Property(
            domainEvent = TargetClassEvent.class
    )
    @PropertyLayout(
            named = "Class",
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
    // target (property)
    // openTargetObject (action)
    // //////////////////////////////////////

    public static class TargetStrEvent extends AbstractPropertyEvent<String> {
        public TargetStrEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public TargetStrEvent(final AuditEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String targetStr;

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.BOOKMARK, name="target")
    @Property(
            domainEvent = TargetStrEvent.class
    )
    @PropertyLayout(
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
    // memberIdentifier (property)
    // //////////////////////////////////////

    public static class MemberIdentifierEvent extends AbstractPropertyEvent<String> {
        public MemberIdentifierEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public MemberIdentifierEvent(final AuditEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String memberIdentifier;

    /**
     * This is the fully-qualified class and property Id, as per
     * {@link Identifier#toClassAndNameIdentityString()}.
     */
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.MEMBER_IDENTIFIER)
    @Property(
            domainEvent = MemberIdentifierEvent.class
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
    // propertyId (property)
    // //////////////////////////////////////

    public static class PropertyIdEvent extends AbstractPropertyEvent<String> {
        public PropertyIdEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public PropertyIdEvent(final AuditEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String propertyId;

    /**
     * This is the property name (without the class).
     */
    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.AuditEntry.PROPERTY_ID)
    @Property(
            domainEvent = PropertyIdEvent.class
    )
    @PropertyLayout(
            hidden = Where.NOWHERE
    )
    @MemberOrder(name="Target",sequence = "20")
    public String getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(final String propertyId) {
        this.propertyId = Util.abbreviated(propertyId, JdoColumnLength.AuditEntry.PROPERTY_ID);
    }
    
    
    // //////////////////////////////////////
    // preValue (property)
    // //////////////////////////////////////

    public static class PreValueEvent extends AbstractPropertyEvent<String> {
        public PreValueEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public PreValueEvent(final AuditEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String preValue;

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.AuditEntry.PROPERTY_VALUE)
    @Property(
            domainEvent = PreValueEvent.class
    )
    @PropertyLayout(
            hidden = Where.NOWHERE
    )
    @MemberOrder(name="Detail",sequence = "6")
    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(final String preValue) {
        this.preValue = Util.abbreviated(preValue, JdoColumnLength.AuditEntry.PROPERTY_VALUE);
    }
    
    
    // //////////////////////////////////////
    // postValue (property)
    // //////////////////////////////////////

    public static class PostValueEvent extends AbstractPropertyEvent<String> {
        public PostValueEvent(final AuditEntry source, final Identifier identifier) {
            super(source, identifier);
        }
        public PostValueEvent(final AuditEntry source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String postValue;

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.AuditEntry.PROPERTY_VALUE)
    @Property(
            domainEvent = PostValueEvent.class
    )
    @PropertyLayout(
            hidden = Where.NOWHERE
    )
    @MemberOrder(name="Detail",sequence = "7")
    public String getPostValue() {
        return postValue;
    }

    public void setPostValue(final String postValue) {
        this.postValue = Util.abbreviated(postValue, JdoColumnLength.AuditEntry.PROPERTY_VALUE);
    }
    
    
    
    // //////////////////////////////////////
    // toString
    // //////////////////////////////////////

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "timestamp,user,targetStr,memberIdentifier");
    }

    
    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////


    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private DomainObjectContainer container;
}
