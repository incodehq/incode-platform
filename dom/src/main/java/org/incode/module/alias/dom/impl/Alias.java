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
package org.incode.module.alias.dom.impl;

import com.google.common.base.Function;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.incode.module.alias.dom.AliasModule;

import javax.inject.Inject;
import javax.jdo.annotations.*;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "incodeAlias",
        table = "Alias"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.NATIVE, column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByAliased", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.Alias "
                        + "WHERE aliasedObjectType == :aliasedObjectType "
                        + "   && aliasedIdentifier == :aliasedIdentifier ")
})
@javax.jdo.annotations.Uniques({
    @Unique(
            name = "AliasLink_aliased_atPath_aliasTypeId_IDX",
            members = { "aliasedObjectType", "aliasedIdentifier", "atPath", "aliasTypeId" }
    )
})
@DomainObject(
        objectType = "incodeAlias.Alias",
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        titleUiEvent = Alias.TitleUiEvent.class,
        iconUiEvent = Alias.IconUiEvent.class,
        cssClassUiEvent = Alias.CssClassUiEvent.class
)
public abstract class Alias implements Comparable<Alias> {

    //region > ui event classes
    public static class TitleUiEvent extends AliasModule.TitleUiEvent<Alias>{}
    public static class IconUiEvent extends AliasModule.IconUiEvent<Alias>{}
    public static class CssClassUiEvent extends AliasModule.CssClassUiEvent<Alias>{}
    //endregion

    //region > domain event classes
    public static abstract class PropertyDomainEvent<T> extends AliasModule.PropertyDomainEvent<Alias, T> { }
    public static abstract class CollectionDomainEvent<T> extends AliasModule.CollectionDomainEvent<Alias, T> { }
    public static abstract class ActionDomainEvent extends AliasModule.ActionDomainEvent<Alias> { }
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


    //region > atPath (property)
    public static class AtPathDomainEvent extends PropertyDomainEvent<String> { }
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
    //endregion

    //region > aliasType (property)
    public static class AliasTypeIdDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = AliasModule.JdoColumnLength.ALIAS_TYPE_ID)
    @Property(
            domainEvent = AliasTypeIdDomainEvent.class
    )
    @PropertyLayout(
            named = "Alias type"
    )
    private String aliasTypeId;
    //endregion

    //region > reference (property)
    public static class ReferenceDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = AliasModule.JdoColumnLength.ALIAS_REFERENCE)
    @Property(
            domainEvent = ReferenceDomainEvent.class
    )
    private String reference;
    //endregion

    //region > aliased (derived property, hooks)
    public static class AliasedDomainEvent extends PropertyDomainEvent<Object> { }

    /**
     * Polymorphic association to the aliased object.
     */
    @javax.jdo.annotations.NotPersistent
    @Programmatic
    public abstract Object getAliased();

    protected abstract void setAliased(final Object aliased);

    //endregion

    //region > aliasedObjectType (property)

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            hidden = Where.EVERYWHERE
    )
    private String aliasedObjectType;

    //endregion

    //region > aliasedIdentifier (property)

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            hidden = Where.EVERYWHERE
    )
    private String aliasedIdentifier;

    //endregion

    //region > remove (action)
    public static class RemoveDomainEvent extends ActionDomainEvent { }
    @Action(
            domainEvent = RemoveDomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            cssClass = "btn-warning",
            cssClassFa = "trash"
    )
    public Object remove() {
        final Object aliased = getAliased();
        aliasRepository.remove(this);
        return aliased;
    }

    //endregion

    //region > Functions
    public static class Functions {
        public static Function<Alias, Object> aliased() {
            return aliased(Object.class);
        }
        public static <T extends Object> Function<Alias, T> aliased(final Class<T> cls) {
            return input -> input != null
                                ? (T)input.getAliased()
                                : null;
        }
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
    AliasRepository aliasRepository;
    //endregion

}
