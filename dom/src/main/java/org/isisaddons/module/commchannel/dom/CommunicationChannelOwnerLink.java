/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.isisaddons.module.commchannel.dom;

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Function;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

import org.isisaddons.module.commchannel.CommChannelModule;
import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

@javax.jdo.annotations.PersistenceCapable(
        schema = "isiscommchannel",
        identityType=IdentityType.DATASTORE
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCommunicationChannel", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.commchannel.dom.CommunicationChannelOwnerLink "
                        + "WHERE communicationChannel == :communicationChannel"),
        @javax.jdo.annotations.Query(
                name = "findByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.commchannel.dom.CommunicationChannelOwnerLink "
                        + "WHERE ownerObjectType == :ownerObjectType "
                        + "   && ownerIdentifier == :ownerIdentifier "),
        @javax.jdo.annotations.Query(
                name = "findByOwnerAndCommunicationChannelType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.commchannel.dom.CommunicationChannelOwnerLink "
                        + "WHERE ownerObjectType == :ownerObjectType "
                        + "   && ownerIdentifier == :ownerIdentifier "
                        + "   && communicationChannelType == :communicationChannelType ")
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "CommunicationChannelOwnerLink_main_idx",
                members = { "ownerObjectType", "ownerIdentifier", "communicationChannelType", "communicationChannel" })
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_commchannel_owner_UNQ", members = {"communicationChannel","ownerObjectType","ownerIdentifier"})
@DomainObject(
        objectType = "comms.CommunicationChannelOwnerLink"
)
public abstract class CommunicationChannelOwnerLink extends PolymorphicAssociationLink<CommunicationChannel, CommunicationChannelOwner, CommunicationChannelOwnerLink> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<CommunicationChannelOwnerLink, T> {
        public PropertyDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<CommunicationChannelOwnerLink, T> {
        public CollectionDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<CommunicationChannelOwnerLink> {
        public ActionDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final CommunicationChannelOwnerLink source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > instantiateEvent (poly pattern)
    public static class InstantiateEvent
            extends PolymorphicAssociationLink.InstantiateEvent<CommunicationChannel, CommunicationChannelOwner, CommunicationChannelOwnerLink> {

        public InstantiateEvent(final Object source, final CommunicationChannel subject, final CommunicationChannelOwner owner) {
            super(CommunicationChannelOwnerLink.class, source, subject, owner);
        }
    }

    //endregion

    //region > constructor
    public CommunicationChannelOwnerLink() {
        super("{polymorphicReference} owns {subject}");
    }
    //endregion

    //region > SubjectPolymorphicReferenceLink API
    @Override
    @Programmatic
    public CommunicationChannel getSubject() {
        return getCommunicationChannel();
    }

    @Override
    @Programmatic
    public void setSubject(final CommunicationChannel subject) {
        setCommunicationChannel(subject);
    }

    @Override
    @Programmatic
    public String getPolymorphicObjectType() {
        return getOwnerObjectType();
    }

    @Override
    @Programmatic
    public void setPolymorphicObjectType(final String polymorphicObjectType) {
        setOwnerObjectType(polymorphicObjectType);
    }

    @Override
    @Programmatic
    public String getPolymorphicIdentifier() {
        return getOwnerIdentifier();
    }

    @Override
    @Programmatic
    public void setPolymorphicIdentifier(final String polymorphicIdentifier) {
        setOwnerIdentifier(polymorphicIdentifier);
    }
    //endregion

    //region > communicationChannel (property)

    public static class CommunicationChannelEvent extends PropertyDomainEvent<CommunicationChannel> {

        public CommunicationChannelEvent(
                final CommunicationChannelOwnerLink source,
                final Identifier identifier) {
            super(source, identifier);
        }

        public CommunicationChannelEvent(
                final CommunicationChannelOwnerLink source,
                final Identifier identifier,
                final CommunicationChannel oldValue,
                final CommunicationChannel newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private CommunicationChannel communicationChannel;
    @Column(
            allowsNull = "false",
            name = "communicationChannelId"
    )
    @Property(
            domainEvent = CommunicationChannelEvent.class
    )
    public CommunicationChannel getCommunicationChannel() {
        return communicationChannel;
    }

    public void setCommunicationChannel(final CommunicationChannel communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    //endregion

    //region > ownerObjectType (property)

    public static class OwnerObjectTypeEvent extends PropertyDomainEvent<String> {

        public OwnerObjectTypeEvent(
                final CommunicationChannelOwnerLink source,
                final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }

        public OwnerObjectTypeEvent(
                final CommunicationChannelOwnerLink source,
                final Identifier identifier) {
            super(source, identifier);
        }
    }

    private String ownerObjectType;

    @Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = OwnerObjectTypeEvent.class
    )
    public String getOwnerObjectType() {
        return ownerObjectType;
    }

    public void setOwnerObjectType(final String ownerObjectType) {
        this.ownerObjectType = ownerObjectType;
    }
    //endregion

    //region > ownerIdentifier (property)

    public static class OwnerIdentifierEvent extends PropertyDomainEvent<String> {

        public OwnerIdentifierEvent(
                final CommunicationChannelOwnerLink source,
                final Identifier identifier) {
            super(source, identifier);
        }

        public OwnerIdentifierEvent(
                final CommunicationChannelOwnerLink source,
                final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String ownerIdentifier;

    @Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = OwnerIdentifierEvent.class
    )
    public String getOwnerIdentifier() {
        return ownerIdentifier;
    }

    public void setOwnerIdentifier(final String ownerIdentifier) {
        this.ownerIdentifier = ownerIdentifier;
    }
    //endregion

    //region > communicationChannelType (property)

    private CommunicationChannelType communicationChannelType;

    /**
     * copy of the {@link #getCommunicationChannel()}'s {@link CommunicationChannel#getType() type}.
     *
     * <p>
     *     To support querying.  This is an immutable property of {@link CommunicationChannel} so
     *     it is safe to copy.
     * </p>
     */
    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.TYPE_ENUM)
    @Property(hidden = Where.EVERYWHERE)
    public CommunicationChannelType getCommunicationChannelType() {
        return communicationChannelType;
    }

    public void setCommunicationChannelType(final CommunicationChannelType communicationChannelType) {
        this.communicationChannelType = communicationChannelType;
    }
    //endregion

    // //////////////////////////////////////

    public static class Functions {
        public static Function<CommunicationChannelOwnerLink, CommunicationChannel> communicationChannel() {
            return communicationChannel(CommunicationChannel.class);
        }
        public static <T extends CommunicationChannel> Function<CommunicationChannelOwnerLink, T> communicationChannel(Class<T> cls) {
            return new Function<CommunicationChannelOwnerLink, T>() {
                @Override
                public T apply(final CommunicationChannelOwnerLink input) {
                    return (T)input.getCommunicationChannel();
                }
            };
        }
        public static Function<CommunicationChannelOwnerLink, CommunicationChannelOwner> owner() {
            return owner(CommunicationChannelOwner.class);
        }

        public static <T extends CommunicationChannelOwner> Function<CommunicationChannelOwnerLink, T> owner(final Class<T> cls) {
            return new Function<CommunicationChannelOwnerLink, T>() {
                @Override
                public T apply(final CommunicationChannelOwnerLink input) {
                    return (T)input.getPolymorphicReference();
                }
            };
        }
    }
}
