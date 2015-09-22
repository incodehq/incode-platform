/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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
package org.isisaddons.module.commchannel.dom;

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.ObjectContracts;

import org.isisaddons.module.commchannel.CommChannelModule;

/**
 * Represents a mechanism for communicating with its
 * {@link CommunicationChannelOwner owner}.
 * 
 * <p>
 * This is an abstract entity; concrete subclasses are {@link PostalAddress
 * postal}, {@link PhoneOrFaxNumber phone/fax} and {@link EmailAddress email}.
 */
@javax.jdo.annotations.PersistenceCapable(
        schema = "isiscommchannel",
        identityType = IdentityType.DATASTORE
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByReferenceAndType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.isisaddons.module.commchannel.dom.CommunicationChannel "
                        + "WHERE reference == :reference "
                        + "&& type == :type")
})
@DomainObject(editing = Editing.DISABLED)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public abstract class CommunicationChannel implements Comparable<CommunicationChannel> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<CommunicationChannel, T> {
        public PropertyDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final CommunicationChannel source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<CommunicationChannel, T> {
        public CollectionDomainEvent(final CommunicationChannel source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final CommunicationChannel source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<CommunicationChannel> {
        public ActionDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final CommunicationChannel source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final CommunicationChannel source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > iconName
    public String iconName() {
        return getType().title().replace(" ", "");
    }
    //endregion

    //region > getId (programmatic)
    @Programmatic
    public String getId() {
        Object objectId = JDOHelper.getObjectId(this);
        if(objectId == null) {
            return "";
        }
        String objectIdStr = objectId.toString();
        final String id = objectIdStr.split("\\[OID\\]")[0];
        return id;
    }
    //endregion

    //region > name (derived property)

    public static class NameDomainEvent extends PropertyDomainEvent<String> {
        public NameDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public NameDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @MemberOrder(sequence = "2")
    @Property(
            domainEvent = NameDomainEvent.class,
            hidden = Where.OBJECT_FORMS
    )
    public String getName() {
        return container.titleOf(this);
    }
    //endregion

    //region > owner (derived property)

    public static class OwnerDomainEvent extends PropertyDomainEvent<CommunicationChannelOwner> {
        public OwnerDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public OwnerDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final CommunicationChannelOwner oldValue,
                final CommunicationChannelOwner newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @Property(
            domainEvent = OwnerDomainEvent.class,
            notPersisted = true,
            editing = Editing.DISABLED
    )
    @PropertyLayout(hidden = Where.PARENTED_TABLES)
    public CommunicationChannelOwner getOwner() {
        final CommunicationChannelOwnerLink link = getOwnerLink();
        return link != null? link.getPolymorphicReference(): null;
    }

    @Programmatic
    public void setOwner(final CommunicationChannelOwner owner) {
        removeOwnerLink();
        final CommunicationChannelOwnerLink link = communicationChannelOwnerLinks.createLink(this, owner);
    }

    private void removeOwnerLink() {
        final CommunicationChannelOwnerLink ownerLink = getOwnerLink();
        if(ownerLink != null) {
            container.remove(ownerLink);
        }
    }

    private CommunicationChannelOwnerLink getOwnerLink() {
        return communicationChannelOwnerLinks.findByCommunicationChannel(this);
    }
    //endregion

    //region > type (property)

    public static class TypeDomainEvent extends PropertyDomainEvent<CommunicationChannelType> {
        public TypeDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public TypeDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final CommunicationChannelType oldValue,
                final CommunicationChannelType newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private CommunicationChannelType type;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.TYPE_ENUM)
    @Property(
            domainEvent = TypeDomainEvent.class,
            hidden = Where.EVERYWHERE
    )
    public CommunicationChannelType getType() {
        return type;
    }

    public void setType(final CommunicationChannelType type) {
        this.type = type;
    }

    //endregion

    //region > reference property)

    public static class ReferenceDomainEvent extends PropertyDomainEvent<String> {
        public ReferenceDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public ReferenceDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String reference;

    /**
     * For import purposes only
     */
    @Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.REFERENCE)
    @Property(
            domainEvent = ReferenceDomainEvent.class,
            hidden = Where.EVERYWHERE
    )
    public String getReference() {
        return reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }
    //endregion

    //region > description (property)

    public static class DescriptionDomainEvent extends PropertyDomainEvent<String> {
        public DescriptionDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public DescriptionDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String description;

    @Column(length = org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength.DESCRIPTION)
    @Property(
            domainEvent = DescriptionDomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.OPTIONAL,
            hidden = Where.ALL_TABLES
    )
    @PropertyLayout(multiLine = 3)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > legal (property)

    public static class LegalDomainEvent extends PropertyDomainEvent<Boolean> {
        public LegalDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public LegalDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final Boolean oldValue,
                final Boolean newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private boolean legal;

    @Property(
            domainEvent = LegalDomainEvent.class,
            editing = Editing.DISABLED
    )
    public boolean isLegal() {
        return legal;
    }

    public void setLegal(final boolean Legal) {
        this.legal = Legal;
    }

    //endregion

    //region > purpose (property)
    private CommunicationChannelPurposeType purpose;

    public static class PurposeDomainEvent extends PropertyDomainEvent<CommunicationChannelPurposeType> {
        public PurposeDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public PurposeDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final CommunicationChannelPurposeType oldValue,
                final CommunicationChannelPurposeType newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @javax.jdo.annotations.Column(allowsNull = "true", length = CommChannelModule.JdoColumnLength.TYPE_ENUM)
    @Property(
            domainEvent = PurposeDomainEvent.class,
            editing = Editing.DISABLED
    )
    public CommunicationChannelPurposeType getPurpose() {
        return purpose;
    }

    public void setPurpose(CommunicationChannelPurposeType purpose) {
        this.purpose = purpose;
    }
    //endregion

    //region > change (action)

    public static class ChangeEvent extends ActionDomainEvent {
        private static final long serialVersionUID = 1L;

        public ChangeEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = ChangeEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public CommunicationChannel change(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description", multiLine = 3)
            final String description,
            @ParameterLayout(named = "Legal")
            final boolean legal,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Purpose")
            final CommunicationChannelPurposeType purpose) {
        setLegal(legal);
        setPurpose(purpose);
        setDescription(description);
        return this;
    }

    public String default0Change() {
        return getDescription();
    }

    public boolean default1Change() {
        return isLegal();
    }

    public CommunicationChannelPurposeType default2Change() {
        return getPurpose();
    }
    //endregion

    //region > remove (action)

    public static class RemoveEvent extends ActionDomainEvent {
        private static final long serialVersionUID = 1L;

        public RemoveEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }

        public CommunicationChannel getReplacement() {
            return (CommunicationChannel) (this.getArguments().isEmpty() ? null : getArguments().get(0));
        }
    }

    @Action(
            domainEvent = RemoveEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public void remove(@ParameterLayout(named = "Replace with") @Parameter(optionality = Optionality.OPTIONAL) CommunicationChannel replacement) {
        removeOwnerLink();
        container.remove(this);
    }


    public SortedSet<CommunicationChannel> choices0Remove() {
        return communicationChannelRepository.findOtherByOwnerAndType(getOwner(), getType(), this);
    }
    //endregion

    //region > toString, compareTo
    public int compareTo(final CommunicationChannel other) {
        return ObjectContracts.compare(this, other, "type", "legal", "id");
    }
    public String toString() {
        return ObjectContracts.toString(this, "type", "legal", "id");
    }
    //endregion

    //region > injected services
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    @Inject
    CommunicationChannelOwnerLinks communicationChannelOwnerLinks;
    @Inject
    DomainObjectContainer container;
    //endregion

}
