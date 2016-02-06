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

import com.google.common.base.Function;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

import org.incode.module.alias.dom.AliasModule;
import org.incode.module.alias.dom.api.aliasable.Aliasable;
import org.incode.module.alias.dom.impl.alias.Alias;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "incodeAlias",
        table = "AliasableLink"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByAlias", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasableLink "
                        + "WHERE alias == :alias"),
        @javax.jdo.annotations.Query(
                name = "findByAliasable", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasableLink "
                        + "WHERE aliasableObjectType == :aliasableObjectType "
                        + "   && aliasableIdentifier == :aliasableIdentifier "),
        @javax.jdo.annotations.Query(
                name = "findByAliasableAndAliasType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasableLink "
                        + "WHERE aliasableObjectType == :aliasableObjectType "
                        + "   && aliasableIdentifier == :aliasableIdentifier "
                        + "   && aliasTypeId == :aliasTypeId"),
        @javax.jdo.annotations.Query(
                name = "findByAliasableAndAtPath", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasableLink "
                        + "WHERE aliasableObjectType == :aliasableObjectType "
                        + "   && aliasableIdentifier == :aliasableIdentifier "
                        + "   && atPath == :atPath "),
        @javax.jdo.annotations.Query(
                name = "findByAliasableAndAtPathAndAliasType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasableLink "
                        + "WHERE aliasableObjectType == :aliasableObjectType "
                        + "   && aliasableIdentifier == :aliasableIdentifier "
                        + "   && atPath == :atPath "
                        + "   && aliasTypeId == :aliasTypeId"),
        @javax.jdo.annotations.Query(
                name = "findByAliasableAndAliasType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.alias.dom.impl.aliaslink.AliasableLink "
                        + "WHERE aliasableObjectType == :aliasableObjectType "
                        + "   && aliasableIdentifier == :aliasableIdentifier "
                        + "   && atPath == :atPath "
                        + "   && aliasTypeId == :aliasTypeId")
})
@javax.jdo.annotations.Indices({
        // there is also a unique constraint (below) on aliasable, atPath, aliasTypeId
        @javax.jdo.annotations.Index(
                name = "AliasableLink_aliasable_aliasType_IDX",
                members = { "aliasableObjectType", "aliasableIdentifier", "aliasTypeId" })
})
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name="AliasableLink_alias_atPath_aliasType_UNQ",
                members = { "aliasableObjectType", "aliasableIdentifier", "atPath", "aliasTypeId" })
})
@DomainObject(
        objectType = "incodeAlias.AliasableLink"
)
public abstract class AliasableLink
        extends PolymorphicAssociationLink<Alias, Aliasable, AliasableLink> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends AliasModule.PropertyDomainEvent<AliasableLink, T> { }
    public static abstract class CollectionDomainEvent<T> extends AliasModule.CollectionDomainEvent<AliasableLink, T> { }
    public static abstract class ActionDomainEvent extends AliasModule.ActionDomainEvent<AliasableLink> { }
    //endregion

    //region > instantiateEvent (poly pattern)
    public static class InstantiateEvent
            extends PolymorphicAssociationLink.InstantiateEvent<Alias, Aliasable, AliasableLink> {

        public InstantiateEvent(final Object source, final Alias subject, final Aliasable owner) {
            super(AliasableLink.class, source, subject, owner);
        }
    }
    //endregion

    //region > constructor
    public AliasableLink() {
        super("{polymorphicReference} has {subject}");
    }
    //endregion

    //region > SubjectPolymorphicReferenceLink API

    /**
     * The subject of the pattern, which (perhaps confusingly in this instance) is actually the
     * {@link #getAlias() event}.
     */
    @Override
    @Programmatic
    public Alias getSubject() {
        return this.getAlias();
    }

    @Override
    @Programmatic
    public void setSubject(final Alias subject) {
        this.setAlias(subject);
    }

    @Override
    @Programmatic
    public String getPolymorphicObjectType() {
        return getAliasableObjectType();
    }

    @Override
    @Programmatic
    public void setPolymorphicObjectType(final String polymorphicObjectType) {
        setAliasableObjectType(polymorphicObjectType);
    }

    @Override
    @Programmatic
    public String getPolymorphicIdentifier() {
        return getAliasableIdentifier();
    }

    @Override
    @Programmatic
    public void setPolymorphicIdentifier(final String polymorphicIdentifier) {
        setAliasableIdentifier(polymorphicIdentifier);
    }
    //endregion


    public static class AliasDomainEvent extends PropertyDomainEvent<Alias> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", name = "aliasId")
    @Property(
            domainEvent = AliasDomainEvent.class,
            editing = Editing.DISABLED
    )
    private Alias alias;

    public static class AliasableObjectTypeDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = AliasableObjectTypeDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String aliasableObjectType;

    public static class AliasableIdentifierDomainEvent extends PropertyDomainEvent<String> {
    }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = AliasableIdentifierDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String aliasableIdentifier;

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


    //region > aliasable (derived property)
    /**
     * Simply returns the {@link #getPolymorphicReference()}.
     */
    @Programmatic
    public Aliasable getAliasable() {
        return getPolymorphicReference();
    }
    //endregion

    //region > Functions
    public static class Functions {
        public static Function<AliasableLink, Alias> alias() {
            return alias(Alias.class);
        }
        public static <T extends Alias> Function<AliasableLink, T> alias(Class<T> cls) {
            return input -> input != null
                                ? (T)input.getAlias()
                                : null;
        }
        public static Function<AliasableLink, Aliasable> aliasable() {
            return aliasable(Aliasable.class);
        }
        public static <T extends Aliasable> Function<AliasableLink, T> aliasable(final Class<T> cls) {
            return input -> input != null
                                ? (T)input.getAliasable()
                                : null;
        }
    }
    //endregion


}
