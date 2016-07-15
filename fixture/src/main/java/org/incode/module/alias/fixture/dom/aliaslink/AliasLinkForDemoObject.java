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
package org.incode.module.alias.fixture.dom.aliaslink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.alias.dom.impl.alias.Object_addAlias;
import org.incode.module.alias.dom.impl.alias.Object_aliases;
import org.incode.module.alias.dom.impl.alias.Object_removeAlias;
import org.incode.module.alias.dom.impl.aliaslink.AliasLink;
import org.incode.module.alias.dom.impl.aliaslink.AliasLinkRepository;
import org.incode.module.alias.fixture.dom.aliasdemoobject.AliasDemoObject;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="aliasdemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "aliasdemo.AliasLinkForDemoObject"
)
public class AliasLinkForDemoObject extends AliasLink {

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class LinkProvider extends AliasLinkRepository.LinkProviderAbstract {
        public LinkProvider() {
            super(AliasDemoObject.class, AliasLinkForDemoObject.class);
        }
    }

    @Mixin
    public static class _addAlias extends Object_addAlias<AliasDemoObject> {
        public _addAlias(final AliasDemoObject aliased) {
            super(aliased);
        }
    }

    @Mixin
    public static class _aliases extends Object_aliases<AliasDemoObject> {
        public _aliases(final AliasDemoObject aliased) {
            super(aliased);
        }
    }

    @Mixin
    public static class _removeAlias extends Object_removeAlias<AliasDemoObject> {
        public _removeAlias(final AliasDemoObject aliased) {
            super(aliased);
        }
    }


    @Override
    public Object getAliased() {
        return getDemoObject();
    }

    @Override
    protected void setAliased(final Object aliased) {
        setDemoObject((AliasDemoObject) aliased);
    }

    //region > demoObject (property)
    private AliasDemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    public AliasDemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final AliasDemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion
}
