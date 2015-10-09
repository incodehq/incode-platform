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
package org.incode.module.commchannel.dom.impl.channel;

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
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.ObjectContracts;

import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

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
                        + "FROM org.incode.module.commchannel.dom.impl.channel.CommunicationChannel "
                        + "WHERE reference == :reference "
                        + "&& type == :type")
})
@DomainObject(editing = Editing.DISABLED)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public abstract class CommunicationChannel<T extends CommunicationChannel<T>> implements Comparable<CommunicationChannel>,
        Locatable {

    //region > event classes
    public static abstract class PropertyDomainEvent<S,T> extends
            CommChannelModule.PropertyDomainEvent<S, T> {
        public PropertyDomainEvent(final S source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<S,T> extends CommChannelModule.CollectionDomainEvent<S, T> {
        public CollectionDomainEvent(final S source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent<S> extends CommChannelModule.ActionDomainEvent<S> {
        public ActionDomainEvent(final S source, final Identifier identifier, final Object... arguments) {
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

    public static class NameEvent extends PropertyDomainEvent<CommunicationChannel,String> {
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

    //region > type (property)

    public static class TypeDomainEvent extends PropertyDomainEvent<CommunicationChannel,CommunicationChannelType> {
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

    public static class DescriptionDomainEvent extends PropertyDomainEvent<CommunicationChannel,String> {
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

    //region > notes (property)

    public static class NotesDomainEvent extends PropertyDomainEvent<CommunicationChannel,String> {
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
    protected DomainObjectContainer container;
    //endregion

}
