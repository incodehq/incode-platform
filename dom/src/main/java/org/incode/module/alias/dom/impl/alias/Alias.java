package org.incode.module.alias.dom.impl.alias;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import org.incode.module.alias.dom.AliasModule;
import org.incode.module.alias.dom.impl.aliaslink.AliasLink;
import org.incode.module.alias.dom.impl.aliaslink.AliasLinkRepository;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        schema = "incodeAlias",
        table = "Alias",
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Queries({
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "Alias_aliasTypeId_atPath_IDX",
                members = { "aliasTypeId", "atPath" })
        // have index for atPath, aliasTypeId, ...
})
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name="Alias_atPath_aliasType_reference_UNQ",
                members = { "atPath", "aliasTypeId", "reference" })
})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        titleUiEvent = Alias.TitleUiEvent.class,
        iconUiEvent = Alias.IconUiEvent.class,
        cssClassUiEvent = Alias.CssClassUiEvent.class
)
public class Alias implements Comparable<Alias> {

    //region > event classes
    public static class TitleUiEvent extends AliasModule.TitleUiEvent<Alias>{}
    public static class IconUiEvent extends AliasModule.IconUiEvent<Alias>{}
    public static class CssClassUiEvent extends AliasModule.CssClassUiEvent<Alias>{}

    public static abstract class PropertyDomainEvent<S,T> extends AliasModule.PropertyDomainEvent<S, T> { }
    public static abstract class CollectionDomainEvent<S,T> extends AliasModule.CollectionDomainEvent<S, T> { }
    public static abstract class ActionDomainEvent<S> extends AliasModule.ActionDomainEvent<S> { }
    //endregion

    //region > title, icon, cssClass
    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService
    public static class TitleSubscriber extends AbstractSubscriber {
        @Subscribe
        public void on(Alias.TitleUiEvent ev) {
            if(ev.getTitle() != null) {
                return;
            }
            ev.setTitle(titleOf(ev.getSource()));
        }
        private String titleOf(final Alias alias) {
            final TitleBuffer buf = new TitleBuffer();
            buf.append(alias.getAtPath());
            buf.append(",");
            buf.append(alias.getAliasTypeId());
            buf.append(",");
            buf.append(alias.getReference());
            buf.append(":");
            buf.append(titleService.titleOf(alias.getAliased()));
            return buf.toString();
        }
        @Inject
        TitleService titleService;
    }

    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService
    public static class IconSubscriber extends AbstractSubscriber {
        @Subscribe
        public void on(Alias.IconUiEvent ev) {
            if(ev.getIconName() != null) {
                return;
            }
            ev.setIconName("");
        }
    }

    /**
     * Implemented as a subscriber so can be overridden by consuming application if required.
     */
    @DomainService
    public static class CssClassSubscriber extends AbstractSubscriber {
        @Subscribe
        public void on(Alias.CssClassUiEvent ev) {
            if(ev.getCssClass() != null) {
                return;
            }
            ev.setCssClass("");
        }
    }
    //endregion


    public static class AtPathDomainEvent extends PropertyDomainEvent<Alias,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true", length = AliasModule.JdoColumnLength.AT_PATH)
    @Property(
            domainEvent = AtPathDomainEvent.class,
            editing = Editing.DISABLED
    )
    @PropertyLayout(
            named = "Application tenancy"
    )
    private String atPath;


    public static class AliasTypeIdDomainEvent extends PropertyDomainEvent<Alias,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = AliasModule.JdoColumnLength.ALIAS_TYPE_ID)
    @Property(
            domainEvent = AliasTypeIdDomainEvent.class
    )
    @PropertyLayout(
            named = "Alias type"
    )
    private String aliasTypeId;


    public static class ReferenceDomainEvent extends PropertyDomainEvent<Alias,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = AliasModule.JdoColumnLength.ALIAS_REFERENCE)
    @Property(
            domainEvent = ReferenceDomainEvent.class
    )
    private String reference;


    //region > aliased (derived property)

    public static class AliasedDomainEvent extends PropertyDomainEvent<Alias,Object> { }

    /**
     * Polymorphic association to (any implementation of) aliased object.
     */
    @Property(
            domainEvent = AliasedDomainEvent.class,
            editing = Editing.DISABLED,
            hidden = Where.PARENTED_TABLES,
            notPersisted = true
    )
    public Object getAliased() {
        final AliasLink link = getAliasLink();
        return link != null? link.getAliased(): null;
    }

    @Programmatic
    public void setAliased(final Object aliased) {
        removeAliasLink();
        aliasLinkRepository.createLink(this, aliased);
    }

    private void removeAliasLink() {
        final AliasLink aliasLink = getAliasLink();
        if(aliasLink != null) {
            repositoryService.remove(aliasLink);
        }
    }

    private AliasLink getAliasLink() {
        if (!repositoryService.isPersistent(this)) {
            return null;
        }
        return aliasLinkRepository.findByAlias(this);
    }
    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "reference", "atPath", "aliasTypeId");
    }

    @Override
    public int compareTo(final Alias other) {
        return ObjectContracts.compare(this, other, "reference", "atPath", "aliasTypeId");
    }

    //endregion

    //region > injected services
    @Inject
    AliasLinkRepository aliasLinkRepository;
    @Inject
    RepositoryService repositoryService;
    //endregion


}
