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
package org.incode.module.alias.dom.impl.aliaslink;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.Unique;

import com.google.common.base.Function;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;

import org.incode.module.alias.dom.AliasModule;
import org.incode.module.alias.dom.impl.alias.Alias;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "incodeAlias",
        table = "AliasLink"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByAlias", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasLink "
                        + "WHERE alias == :alias"),
        @javax.jdo.annotations.Query(
                name = "findByAliased", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasLink "
                        + "WHERE aliasedObjectType == :aliasedObjectType "
                        + "   && aliasedIdentifier == :aliasedIdentifier ")
})
@javax.jdo.annotations.Uniques({
    @Unique(
            name = "AliasLink_aliased_alias_IDX",
            members = { "aliasedObjectType", "aliasedIdentifier", "alias" }
    )
})
@DomainObject(
        objectType = "incodeAlias.AliasLink"
)
public abstract class AliasLink {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends AliasModule.PropertyDomainEvent<AliasLink, T> { }
    public static abstract class CollectionDomainEvent<T> extends AliasModule.CollectionDomainEvent<AliasLink, T> { }
    public static abstract class ActionDomainEvent extends AliasModule.ActionDomainEvent<AliasLink> { }
    //endregion

    //region > constructor
    public AliasLink() {
        //super("{polymorphicReference} has {subject}");
    }
    //endregion

    //region > alias (property)

    public static class AliasDomainEvent extends PropertyDomainEvent<Alias> { }

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", name = "aliasId")
    @Property(
            domainEvent = AliasDomainEvent.class,
            editing = Editing.DISABLED
    )
    private Alias alias;

    //endregion

    //region > aliasedObjectType (property)

    public static class AliasedObjectTypeDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = AliasedObjectTypeDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String aliasedObjectType;

    //endregion

    //region > aliasedIdentifier (property)

    public static class AliasedIdentifierDomainEvent extends PropertyDomainEvent<String> {
    }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = AliasedIdentifierDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String aliasedIdentifier;

    //endregion

    //region > aliased (hooks)

    @NotPersistent
    @Programmatic
    public abstract Object getAliased();
    protected abstract void setAliased(final Object aliased);

    //endregion

    //region > Functions
    public static class Functions {
        public static Function<AliasLink, Alias> alias() {
            return alias(Alias.class);
        }
        public static <T extends Alias> Function<AliasLink, T> alias(Class<T> cls) {
            return input -> input != null
                                ? (T)input.getAlias()
                                : null;
        }
        public static Function<AliasLink, Object> aliased() {
            return aliased(Object.class);
        }
        public static <T extends Object> Function<AliasLink, T> aliased(final Class<T> cls) {
            return input -> input != null
                                ? (T)input.getAliased()
                                : null;
        }
    }
    //endregion

}
