package org.incode.example.commchannel.dom.impl.ownerlink;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Function;
import com.google.common.eventbus.Subscribe;

import org.axonframework.eventhandling.annotation.EventHandler;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.title.TitleService;

import org.incode.example.commchannel.CommChannelModule;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "incodeCommChannel",
        table = "CommunicationChannelOwnerLink"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByCommunicationChannel", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink "
                        + "WHERE communicationChannel == :communicationChannel"),
        @javax.jdo.annotations.Query(
                name = "findByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink "
                        + "WHERE ownerStr == :ownerStr "),
        @javax.jdo.annotations.Query(
                name = "findByOwnerAndCommunicationChannelType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink "
                        + "WHERE ownerStr == :ownerStr "
                        + "   && communicationChannelType == :communicationChannelType ")
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "CommunicationChannelOwnerLink_main_idx",
                members = { "ownerStr", "communicationChannelType", "communicationChannel" })
})
@javax.jdo.annotations.Unique(name="CommunicationChannelOwnerLink_commchannel_UNQ", members = {"communicationChannel"})
@DomainObject(
        objectType = "incodeCommChannel.CommunicationChannelOwnerLink"
)
@DomainObjectLayout(
        titleUiEvent = CommunicationChannelOwnerLink.TitleUiEvent.class,
        iconUiEvent = CommunicationChannelOwnerLink.IconUiEvent.class,
        cssClassUiEvent = CommunicationChannelOwnerLink.CssClassUiEvent.class
)
public abstract class CommunicationChannelOwnerLink {

    //region > ui event classes
    public static class TitleUiEvent extends CommChannelModule.TitleUiEvent<CommunicationChannelOwnerLink>{}
    public static class IconUiEvent extends CommChannelModule.IconUiEvent<CommunicationChannelOwnerLink>{}
    public static class CssClassUiEvent extends CommChannelModule.CssClassUiEvent<CommunicationChannelOwnerLink>{}
    //endregion

    //region > domain events
    public static abstract class PropertyDomainEvent<T> extends CommChannelModule.PropertyDomainEvent<CommunicationChannelOwnerLink, T> { }
    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<CommunicationChannelOwnerLink, T> { }
    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<CommunicationChannelOwnerLink> { }
    //endregion

    //region > title, icon, cssClass
    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class TitleSubscriber extends AbstractSubscriber {
        @EventHandler
        @Subscribe
        public void on(CommunicationChannelOwnerLink.TitleUiEvent ev) {
            if(ev.getTitle() != null) {
                return;
            }
            ev.setTitle(titleOf(ev.getSource()));
        }
        private String titleOf(final CommunicationChannelOwnerLink communicationChannelOwnerLink) {
            return String.format("%s: %s",
                    titleService.titleOf(communicationChannelOwnerLink.getOwner()),
                    // hmm; if using guava, can't call events within events...
                    communicationChannelOwnerLink.getCommunicationChannelType());
        }
        @Inject
        TitleService titleService;
    }

    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class IconSubscriber extends AbstractSubscriber {
        @EventHandler
        @Subscribe
        public void on(CommunicationChannelOwnerLink.IconUiEvent ev) {
            if(ev.getIconName() != null) {
                return;
            }
            ev.setIconName("");
        }
    }

    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class CssClassSubscriber extends AbstractSubscriber {
        @EventHandler
        @Subscribe
        public void on(CommunicationChannelOwnerLink.CssClassUiEvent ev) {
            if(ev.getCssClass() != null) {
                return;
            }
            ev.setCssClass("");
        }
    }
    //endregion


    //region > ownerStr (property)
    public static class OwnerStrDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.BOOKMARK)
    @Property(
            domainEvent = OwnerStrDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String ownerStr;
    //endregion

    //region > owner (derived property, hooks)
    /**
     * Polymorphic association to the owning object.
     */
    @Programmatic
    public abstract Object getOwner();
    protected abstract void setOwner(Object object);
    //endregion

    //region > communicationChannel (property)
    public static class CommunicationChannelDomainEvent extends PropertyDomainEvent<CommunicationChannel> { }
    @Getter @Setter
    @Column(
            allowsNull = "false",
            name = "communicationChannelId"
    )
    @Property(
            domainEvent = CommunicationChannelDomainEvent.class
    )
    private CommunicationChannel communicationChannel;
    //endregion

    //region > communicationChannelType (property)

    /**
     * copy of the {@link #getCommunicationChannel()}'s {@link CommunicationChannel#getType() type}.
     *
     * <p>
     *     To support querying.  This is an immutable property of {@link CommunicationChannel} so
     *     it is safe to copy.
     * </p>
     */
    @Getter @Setter
    @Column(allowsNull = "false", length = CommChannelModule.JdoColumnLength.TYPE_ENUM)
    @Property(hidden = Where.EVERYWHERE)
    private CommunicationChannelType communicationChannelType;
    //endregion

    //region > Functions
    public static class Functions {
        public static Function<CommunicationChannelOwnerLink, CommunicationChannel> communicationChannel() {
            return communicationChannel(CommunicationChannel.class);
        }
        public static <T extends CommunicationChannel> Function<CommunicationChannelOwnerLink, T> communicationChannel(Class<T> cls) {
            return input -> (T)input.getCommunicationChannel();
        }
        public static Function<CommunicationChannelOwnerLink, Object> owner() {
            return owner(Object.class);
        }
        public static <T extends Object> Function<CommunicationChannelOwnerLink, T> owner(final Class<T> cls) {
            return input -> (T)input.getOwner();
        }
    }
    //endregion

}
