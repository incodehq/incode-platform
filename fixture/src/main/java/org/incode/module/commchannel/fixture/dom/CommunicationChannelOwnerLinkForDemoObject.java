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
package org.incode.module.commchannel.fixture.dom;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

@javax.jdo.annotations.PersistenceCapable(
        schema="commchanneldemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "commchanneldemo.CommunicationChannelOwnerLinkForDemoObject"
)
public class CommunicationChannelOwnerLinkForDemoObject extends CommunicationChannelOwnerLink {

    //region > instantiationSubscriber, setPolymorphicReference

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class InstantiationSubscriber extends AbstractSubscriber {
        @Programmatic
        @Subscribe
        public void on(final InstantiateEvent ev) {
            if(ev.getPolymorphicReference() instanceof CommChannelDemoObject) {
                ev.setSubtype(CommunicationChannelOwnerLinkForDemoObject.class);
            }
        }
    }

    @Override
    public void setPolymorphicReference(final CommunicationChannelOwner polymorphicReference) {
        super.setPolymorphicReference(polymorphicReference);
        setDemoObject((CommChannelDemoObject) polymorphicReference);
    }

    //endregion

    //region > demoObject (property)
    private CommChannelDemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "partyId"
    )
    @MemberOrder(sequence = "1")
    public CommChannelDemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final CommChannelDemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion

}
