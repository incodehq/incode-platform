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
package org.incode.module.alias.fixture.dom.aliasablelink;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.alias.dom.api.aliasable.Aliasable;
import org.incode.module.alias.dom.impl.aliaslink.AliasableLink;
import org.incode.module.alias.dom.impl.alias.AliasRepository;
import org.incode.module.alias.fixture.dom.aliasdemoobject.AliasDemoObject;

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema="aliasdemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "aliasdemo.AliasableLinkForDemoObject"
)
public class AliasableLinkForDemoObject extends AliasableLink {

    //region > instantiationSubscriber, setPolymorphicReference
    @DomainService(nature = NatureOfService.DOMAIN)
    @DomainServiceLayout(menuOrder = "1")
    public static class InstantiationSubscriber extends AbstractSubscriber {

        @Programmatic
        @Subscribe
        public void on(final InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof AliasDemoObject) {
                ev.setSubtype(AliasableLinkForDemoObject.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final Aliasable polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setDemoObject((AliasDemoObject) polymorphicReference);
    }
    //endregion

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

    //region > injected services
    @javax.inject.Inject
    private AliasRepository aliasRepository;
    //endregion

}
