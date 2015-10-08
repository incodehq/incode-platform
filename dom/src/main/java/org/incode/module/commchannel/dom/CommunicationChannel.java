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
package org.incode.module.commchannel.dom;

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

import org.incode.module.commchannel.CommChannelModule;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;

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
                        + "FROM CommunicationChannel "
                        + "WHERE reference == :reference "
                        + "&& type == :type")
})
@DomainObject(editing = Editing.DISABLED)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public abstract class CommunicationChannel<T extends CommunicationChannel<T>> implements Comparable<CommunicationChannel>,
        Locatable {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends
            CommChannelModule.PropertyDomainEvent<CommunicationChannel, T> {
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

    public static class NameEvent extends PropertyDomainEvent<String> {
        public NameEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public NameEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @Property(
            domainEvent = NameEvent.class,
            hidden = Where.OBJECT_FORMS
    )
    public String getName() {
        return container.titleOf(this);
    }
    //endregion

    //region > owner (derived property)

    public static class OwnerEvent extends PropertyDomainEvent<CommunicationChannelOwner> {
        public OwnerEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public OwnerEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final CommunicationChannelOwner oldValue,
                final CommunicationChannelOwner newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @Property(
            domainEvent = OwnerEvent.class,
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
        communicationChannelOwnerLinks.createLink(this, owner);
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
            optionality = Optionality.OPTIONAL
    )
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > change (action)

    public static class UpdateDescription extends ActionDomainEvent {
        private static final long serialVersionUID = 1L;

        public UpdateDescription(
                final CommunicationChannel source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateDescription.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public T updateDescription(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description")
            final String description) {
        setDescription(description);
        return (T)this;
    }

    public String default0UpdateDescription() {
        return getDescription();
    }

    //endregion

    //region > notes (property)

    public static class NotesDomainEvent extends PropertyDomainEvent<String> {
        public NotesDomainEvent(final CommunicationChannel source, final Identifier identifier) {
            super(source, identifier);
        }
        public NotesDomainEvent(
                final CommunicationChannel source,
                final Identifier identifier,
                final String oldValue,
                final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String notes;

    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = NotesDomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(multiLine = 10)
    public String getNotes() {
        return notes;
    }

    public void setNotes(final String Notes) {
        this.notes = Notes;
    }
    //endregion

    //region > change (action)

    public static class UpdateNotes extends ActionDomainEvent {
        private static final long serialVersionUID = 1L;

        public UpdateNotes(
                final CommunicationChannel source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = UpdateNotes.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public T updateNotes(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = 10)
            final String Notes) {
        setNotes(Notes);
        return (T)this;
    }

    public String default0UpdateNotes() {
        return getNotes();
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
    public CommunicationChannelOwner remove(
            @ParameterLayout(named = "Replace with")
            @Parameter(optionality = Optionality.OPTIONAL)
            CommunicationChannel replacement) {
        final CommunicationChannelOwner owner = getOwner();
        removeOwnerLink();
        container.remove(this);
        return owner;
    }


    public SortedSet<CommunicationChannel> choices0Remove() {
        return communicationChannelRepository.findOtherByOwnerAndType(getOwner(), getType(), this);
    }
    //endregion

    //region > Locatable API

    /**
     * Default implementation just returns <tt>null</tt>
     *
     * <p>
     *     It's necessary for {@link CommunicationChannel} to implement this in order that the gmap3 wicket component
     *     will return any collection of {@link CommunicationChannel}s on a map.
     * </p>
     */
    @Programmatic
    @Override
    public Location getLocation() {
        return null;
    }
    //endregion

    //region > toString, compareTo
    public int compareTo(final CommunicationChannel other) {
        return ObjectContracts.compare(this, other, "type", "id");
    }
    public String toString() {
        return ObjectContracts.toString(this, "type", "id");
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
