/*
 *  Copyright 2015 incode.org
 *
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
package org.incode.module.commchannel.dom.impl.ownerlink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Function;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.api.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        schema = "incodeCommChannel",
        identityType=IdentityType.DATASTORE
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCommunicationChannel", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink "
                        + "WHERE communicationChannel == :communicationChannel"),
        @javax.jdo.annotations.Query(
                name = "findByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink "
                        + "WHERE ownerObjectType == :ownerObjectType "
                        + "   && ownerIdentifier == :ownerIdentifier "),
        @javax.jdo.annotations.Query(
                name = "findByOwnerAndCommunicationChannelType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink "
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
        objectType = "incodeCommunicationChannel.CommunicationChannelOwnerLink"
)
public abstract class CommunicationChannelOwnerLink extends PolymorphicAssociationLink<CommunicationChannel, CommunicationChannelOwner, CommunicationChannelOwnerLink> {

    //region > events
    public static abstract class PropertyDomainEvent<S,T> extends CommChannelModule.PropertyDomainEvent<S, T> { }
    public static abstract class CollectionDomainEvent<S,T> extends CommChannelModule.CollectionDomainEvent<S, T> { }
    public static abstract class ActionDomainEvent<S> extends CommChannelModule.ActionDomainEvent<S> { }
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

    public static class CommunicationChannelEvent
            extends PropertyDomainEvent<CommunicationChannelOwnerLink,CommunicationChannel> { }
    @Getter @Setter
    @Column(
            allowsNull = "false",
            name = "communicationChannelId"
    )
    @Property(
            domainEvent = CommunicationChannelEvent.class
    )
    private CommunicationChannel communicationChannel;


    public static class OwnerObjectTypeEvent extends PropertyDomainEvent<CommunicationChannelOwnerLink, String> { }
    @Getter @Setter
    @Column(
            allowsNull = "false",
            length = CommChannelModule.JdoColumnLength.OBJECT_TYPE
    )
    @Property(
            domainEvent = OwnerObjectTypeEvent.class
    )
    private String ownerObjectType;


    public static class OwnerIdentifierEvent extends PropertyDomainEvent<CommunicationChannelOwnerLink, String> { }
    @Getter @Setter
    @Column(
            allowsNull = "false",
            length = CommChannelModule.JdoColumnLength.OBJECT_IDENTIFIER
    )
    @Property(
            domainEvent = OwnerIdentifierEvent.class
    )
    private String ownerIdentifier;


    /**
     * copy of the {@link #getCommunicationChannel()}'s {@link CommunicationChannel#getType() type}.
     *
     * <p>
     *     To support querying.  This is an immutable property of {@link CommunicationChannel} so
     *     it is safe to copy.
     * </p>
     */
    @Getter @Setter
    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.TYPE_ENUM)
    @Property(hidden = Where.EVERYWHERE)
    private CommunicationChannelType communicationChannelType;


    //region > Functions
    public static class Functions {
        public static Function<CommunicationChannelOwnerLink, CommunicationChannel> communicationChannel() {
            return communicationChannel(CommunicationChannel.class);
        }
        public static <T extends CommunicationChannel> Function<CommunicationChannelOwnerLink, T> communicationChannel(Class<T> cls) {
            return input -> (T)input.getCommunicationChannel();
        }
        public static Function<CommunicationChannelOwnerLink, CommunicationChannelOwner> owner() {
            return owner(CommunicationChannelOwner.class);
        }
        public static <T extends CommunicationChannelOwner> Function<CommunicationChannelOwnerLink, T> owner(final Class<T> cls) {
            return input -> (T)input.getPolymorphicReference();
        }
    }
    //endregion

}
