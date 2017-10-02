package org.incode.module.commchannel.dom.impl.channel;

import java.util.Collection;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.PostalAddress;
import org.incode.module.commchannel.dom.impl.purpose.CommunicationChannelPurposeService;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a mechanism for communicating with its owner.
 *
 * <p>
 * This is an abstract entity; concrete subclasses are {@link PostalAddress
 * postal}, {@link PhoneOrFaxNumber phone/fax} and {@link EmailAddress email}.
 */
@javax.jdo.annotations.PersistenceCapable(
        schema = "incodeCommChannel",
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
@DomainObject(
        objectType = "incodeCommChannel.CommunicationChannel"
)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public abstract class CommunicationChannel<T extends CommunicationChannel<T>> implements Comparable<CommunicationChannel>,
        Locatable {

    //region > events
    public static abstract class PropertyDomainEvent<S,T>
            extends CommChannelModule.PropertyDomainEvent<S, T> {}

    public static abstract class CollectionDomainEvent<S,T>
            extends CommChannelModule.CollectionDomainEvent<S, T> {}

    public static abstract class ActionDomainEvent<S>
            extends CommChannelModule.ActionDomainEvent<S> { }
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


    public static class NameDomainEvent extends PropertyDomainEvent<CommunicationChannel,String> {}
    @Property(
            domainEvent = NameDomainEvent.class,
            editing = Editing.DISABLED,
            hidden = Where.OBJECT_FORMS
    )
    public String getName() {
        return titleService.titleOf(this);
    }


    public static class TypeDomainEvent extends PropertyDomainEvent<CommunicationChannel,CommunicationChannelType> { }
    @Getter @Setter
    @Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.TYPE_ENUM)
    @Property(
            domainEvent = TypeDomainEvent.class,
            editing = Editing.DISABLED,
            hidden = Where.EVERYWHERE
    )
    private CommunicationChannelType type;


    public static class PurposeDomainEvent extends PropertyDomainEvent<CommunicationChannel,String> { }
    @Getter @Setter
    @Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.PURPOSE)
    @Property(
            domainEvent = PurposeDomainEvent.class,
            editing = Editing.ENABLED,
            maxLength = CommChannelModule.JdoColumnLength.PURPOSE,
            optionality = Optionality.MANDATORY
    )
    private String purpose;

    public Collection<String> choicesPurpose() {
        return communicationChannelPurposeService.purposesFor(getType(), mixinOwner().$$());
    }

    private CommunicationChannel_owner mixinOwner() {
        return factoryService.mixin(CommunicationChannel_owner.class, this);
    }


    public static class CurrentDomainEvent extends PropertyDomainEvent<CommunicationChannel,Boolean> { }
    @Getter @Setter
    @Column(allowsNull = "false")
    @Property(
            domainEvent = CurrentDomainEvent.class,
            editing = Editing.DISABLED,
            optionality = Optionality.MANDATORY)
    public Boolean current;

    public static class NotesDomainEvent extends PropertyDomainEvent<CommunicationChannel,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true", jdbcType="CLOB")
    @Property(
            domainEvent = NotesDomainEvent.class,
            editing = Editing.ENABLED,
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(multiLine = 10)
    private String notes;


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
    TitleService titleService;

    @Inject
    private FactoryService factoryService;

    @Inject
    private CommunicationChannelPurposeService communicationChannelPurposeService;
    //endregion


}
