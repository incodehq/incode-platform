package org.incode.module.alias.dom.impl.alias;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Function;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import org.incode.module.alias.dom.AliasModule;
import org.incode.module.alias.dom.api.aliasable.Aliasable;
import org.incode.module.alias.dom.impl.aliaslink.AliasableLink;
import org.incode.module.alias.dom.impl.aliaslink.AliasableLinkRepository;

import lombok.Getter;
import lombok.Setter;

/**
 * An event that has or is scheduled to occur at some point in time, pertaining
 * to an {@link Aliasable}.
 */
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
                name = "Alias_alias_idx",
                members = { "alias" })
})
@DomainObject(
        editing = Editing.DISABLED
)
public class Alias implements Comparable<Alias> {

    //region > injected services
    @Inject
    AliasableLinkRepository aliasableLinkRepository;
    @Inject
    DomainObjectContainer container;
    //endregion

    //region > event classes
    public static abstract class PropertyDomainEvent<S,T> extends AliasModule.PropertyDomainEvent<S, T> { }
    public static abstract class CollectionDomainEvent<S,T> extends AliasModule.CollectionDomainEvent<S, T> { }
    public static abstract class ActionDomainEvent<S> extends AliasModule.ActionDomainEvent<S> { }
    //endregion

    //region > title
    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(container.titleOf(getAliasable()));
        return buf.toString();
    }

    //endregion


    public static class AtPathDomainEvent extends PropertyDomainEvent<Alias,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true", length = AliasModule.JdoColumnLength.AT_PATH)
    @Property(
            domainEvent = AtPathDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String atPath;


    public static class AliasTypeIdDomainEvent extends PropertyDomainEvent<Alias,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = AliasModule.JdoColumnLength.ALIAS_TYPE_ID)
    @Property(
            domainEvent = AliasTypeIdDomainEvent.class
    )
    private String aliasTypeId;


    public static class AliasDomainEvent extends PropertyDomainEvent<Alias,String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = AliasModule.JdoColumnLength.ALIAS_REFERENCE)
    @Property(
            domainEvent = AliasDomainEvent.class
    )
    private String alias;


    //region > aliasable (derived property)

    public static class AliasableDomainEvent extends PropertyDomainEvent<Alias,Aliasable> { }

    /**
     * Polymorphic association to (any implementation of) {@link Aliasable}.
     */
    @Property(
            domainEvent = AliasableDomainEvent.class,
            editing = Editing.DISABLED,
            hidden = Where.PARENTED_TABLES,
            notPersisted = true
    )
    public Aliasable getAliasable() {
        final AliasableLink link = getAliasableLink();
        return link != null? link.getPolymorphicReference(): null;
    }

    @Programmatic
    public void setAliasable(final Aliasable aliasable) {
        removeAliasableLink();
        aliasableLinkRepository.createLink(this, aliasable);
    }

    private void removeAliasableLink() {
        final AliasableLink aliasableLink = getAliasableLink();
        if(aliasableLink != null) {
            container.remove(aliasableLink);
        }
    }

    private AliasableLink getAliasableLink() {
        if (!container.isPersistent(this)) {
            return null;
        }
        return aliasableLinkRepository.findByAlias(this);
    }
    //endregion

    //region > Functions

    public final static class Functions {
        private Functions() {}
        public final static Function<Alias, String> GET_AT_PATH = input -> input.getAtPath();
        public final static Function<Alias, String> GET_ALIAS_TYPE_ID = input -> input.getAliasTypeId();
    }
    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "alias", "atPath", "aliasTypeId");
    }

    @Override
    public int compareTo(final Alias other) {
        return ObjectContracts.compare(this, other, "alias", "atPath", "aliasTypeId");
    }

    //endregion

}
