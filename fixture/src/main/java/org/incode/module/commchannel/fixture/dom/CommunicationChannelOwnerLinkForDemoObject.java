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

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.commchannel.dom.impl.channel.T_communicationChannels;
import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;

@javax.jdo.annotations.PersistenceCapable(
        schema="commchanneldemo")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        objectType = "commchanneldemo.CommunicationChannelOwnerLinkForDemoObject"
)
public class CommunicationChannelOwnerLinkForDemoObject extends CommunicationChannelOwnerLink {


    //region > demoObject (property)
    private CommChannelDemoObject demoObject;

    @Column(
            allowsNull = "false",
            name = "demoObjectId"
    )
    public CommChannelDemoObject getDemoObject() {
        return demoObject;
    }

    public void setDemoObject(final CommChannelDemoObject demoObject) {
        this.demoObject = demoObject;
    }
    //endregion


    //region > owner (hook, derived)
    @Override
    public Object getOwner() {
        return getDemoObject();
    }

    @Override
    protected void setOwner(final Object object) {
        setDemoObject(demoObject);
    }
    //endregion


    //region > SubtypeProvider SPI implementation
    @DomainService(nature = NatureOfService.DOMAIN)
    public static class SubtypeProvider extends CommunicationChannelOwnerLinkRepository.SubtypeProviderAbstract {
        public SubtypeProvider() {
            super(CommChannelDemoObject.class, CommunicationChannelOwnerLinkForDemoObject.class);
        }
    }
    //endregion

    //region > mixins

    @Mixin
    public static class _communicationChannels extends T_communicationChannels<CommChannelDemoObject> {
        public _communicationChannels(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    @Mixin
    public static class addEmailAddress extends T_addEmailAddress<CommChannelDemoObject> {
        public addEmailAddress(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    @Mixin
    public static class _addPhoneOrFaxNumber extends T_addPhoneOrFaxNumber<CommChannelDemoObject> {
        public _addPhoneOrFaxNumber(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    @Mixin
    public static class _addPostalAddress extends T_addPostalAddress<CommChannelDemoObject> {
        public _addPostalAddress(final CommChannelDemoObject owner) {
            super(owner);
        }
    }

    //endregion


}
