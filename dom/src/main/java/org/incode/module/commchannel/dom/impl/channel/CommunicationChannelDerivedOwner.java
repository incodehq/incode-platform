/*
 *
Copyright 2015 incode.org
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package org.incode.module.commchannel.dom.impl.channel;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannelDerivedOwner {


    public static class OwnerEvent extends
            CommunicationChannel.PropertyDomainEvent<CommunicationChannelDerivedOwner,CommunicationChannelOwner> {
        public OwnerEvent(
                final CommunicationChannelDerivedOwner source,
                final Identifier identifier,
                final CommunicationChannelOwner oldValue,
                final CommunicationChannelOwner newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @Property(
            domainEvent = OwnerEvent.class,
            notPersisted = true,
            editing = Editing.DISABLED
    )
    @PropertyLayout(
            hidden = Where.PARENTED_TABLES
    )
    public CommunicationChannelOwner owner(final CommunicationChannel communicationChannel) {
        final CommunicationChannelOwnerLink link = getOwnerLink(communicationChannel);
        return link != null? link.getPolymorphicReference(): null;
    }

    @Programmatic
    public void setOwner(
            final CommunicationChannel communicationChannel,
            final CommunicationChannelOwner owner) {
        removeOwnerLink(communicationChannel);
        communicationChannelOwnerLinkRepository.createLink(communicationChannel, owner);
    }

    CommunicationChannelOwnerLink getOwnerLink(final CommunicationChannel communicationChannel) {
        return communicationChannelOwnerLinkRepository.findByCommunicationChannel(communicationChannel);
    }

    void removeOwnerLink(final CommunicationChannel communicationChannel) {
        final CommunicationChannelOwnerLink ownerLink = getOwnerLink(communicationChannel);
        if(ownerLink != null) {
            container.remove(ownerLink);
        }
    }


    @Inject
    CommunicationChannelOwnerLinkRepository communicationChannelOwnerLinkRepository;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    @Inject
    DomainObjectContainer container;




}
