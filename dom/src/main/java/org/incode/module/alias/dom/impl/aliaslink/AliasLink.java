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
                        + "   && aliasedIdentifier == :aliasedIdentifier "),
        @javax.jdo.annotations.Query(
                name = "findByAliasedAndAliasType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasLink "
                        + "WHERE aliasedObjectType == :aliasedObjectType "
                        + "   && aliasedIdentifier == :aliasedIdentifier "
                        + "   && aliasTypeId == :aliasTypeId"),
        @javax.jdo.annotations.Query(
                name = "findByAliasedAndAtPath", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasLink "
                        + "WHERE aliasedObjectType == :aliasedObjectType "
                        + "   && aliasedIdentifier == :aliasedIdentifier "
                        + "   && atPath == :atPath "),
        @javax.jdo.annotations.Query(
                name = "findByAliasedAndAtPathAndAliasType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasLink "
                        + "WHERE aliasedObjectType == :aliasedObjectType "
                        + "   && aliasedIdentifier == :aliasedIdentifier "
                        + "   && atPath == :atPath "
                        + "   && aliasTypeId == :aliasTypeId"),
        @javax.jdo.annotations.Query(
                name = "findByAliasedAndAliasType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasLink "
                        + "WHERE aliasedObjectType == :aliasedObjectType "
                        + "   && aliasedIdentifier == :aliasedIdentifier "
                        + "   && atPath == :atPath "
                        + "   && aliasTypeId == :aliasTypeId")
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "AliasLink_aliased_aliasType_atPath_IDX",
                members = { "aliasedObjectType", "aliasedIdentifier", "aliasTypeId", "atPath" },
                unique = "true"
        ),
        @javax.jdo.annotations.Index(
                name = "AliasLink_aliased_atPath_aliasType_IDX",
                members = { "aliasedObjectType", "aliasedIdentifier", "atPath", "aliasTypeId" },
                unique = "true"
        ),
        @javax.jdo.annotations.Index(
                name = "AliasLink_atPath_aliasType_aliased_IDX",
                members = { "atPath", "aliasTypeId", "aliasedObjectType", "aliasedIdentifier" },
                unique = "true"
        ),
        @javax.jdo.annotations.Index(
                name = "AliasLink_aliasType_atPath_aliased_IDX",
                members = { "aliasTypeId", "atPath", "aliasedObjectType", "aliasedIdentifier" },
                unique = "true"
        )
})
@javax.jdo.annotations.Uniques({
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

    public static class AliasDomainEvent extends PropertyDomainEvent<Alias> { }
    public static class AliasedObjectTypeDomainEvent extends PropertyDomainEvent<String> { }

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", name = "aliasId")
    @Property(
            domainEvent = AliasDomainEvent.class,
            editing = Editing.DISABLED
    )
    private Alias alias;

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = AliasedObjectTypeDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String aliasedObjectType;


    public static class AliasedIdentifierDomainEvent extends PropertyDomainEvent<String> {
    }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = AliasedIdentifierDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String aliasedIdentifier;


    public static class AtPathDomainEvent extends PropertyDomainEvent<String> { }
    /**
     * Copy of the {@link #getAlias() alias}' {@link Alias#getAtPath() at path}, to support querying.
     */
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true", length= AliasModule.JdoColumnLength.AT_PATH)
    @Property(
            domainEvent = AtPathDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String atPath;


    public static class AliasTypeIdDomainEvent extends PropertyDomainEvent<String> { }
    /**
     * Copy of the {@link #getAlias() alias}' {@link Alias#getAliasTypeId() alias type}, to support querying.
     */
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            domainEvent = AliasTypeIdDomainEvent.class
    )
    private String aliasTypeId;


    @NotPersistent
    @Programmatic
    public abstract Object getAliased();
    protected abstract void setAliased(final Object aliased);


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
