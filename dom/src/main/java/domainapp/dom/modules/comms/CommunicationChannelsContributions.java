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
package domainapp.dom.modules.comms;

import domainapp.dom.modules.poly.PolymorphicLinkInstantiateEvent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.NonRecoverableException;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.eventbus.EventBusService;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
@DomainServiceLayout(menuOrder = "10")
public class CommunicationChannelsContributions {

    //region > createCommunicationChannel (action)

    public static class CommunicationChannelOwnerLinkInstantiateEvent
            extends PolymorphicLinkInstantiateEvent<CommunicationChannelOwnerLink, CommunicationChannel, CommunicationChannelOwner> {

        public CommunicationChannelOwnerLinkInstantiateEvent(final Object source, final CommunicationChannel subject, final CommunicationChannelOwner owner) {
            super(CommunicationChannelOwnerLink.class, source, subject, owner);
        }
    }


    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="CommunicationChannel",sequence = "3")
    public CommunicationChannel createCommunicationChannel(
            final CommunicationChannelOwner owner,
            @ParameterLayout(named = "Details")
            final String details) {

        final CommunicationChannel communicationChannel = container.newTransientInstance(CommunicationChannel.class);
        communicationChannel.setDetails(details);

        final CommunicationChannelOwnerLinkInstantiateEvent event =
                new CommunicationChannelOwnerLinkInstantiateEvent(this, communicationChannel, owner);
        eventBusService.post(event);

        final Class<? extends CommunicationChannelOwnerLink> subtype = event.getSubtype();
        if(subtype == null) {
            throw new NonRecoverableException("Cannot create link to " + container.titleOf(owner) + ", no subtype provided");
        }

        final CommunicationChannelOwnerLink ownerLink = container.newTransientInstance(subtype);
        ownerLink.setPolymorphicReference(owner);

        // must persist the channel first
        container.persist(communicationChannel);
        container.flush();

        // second half of the 1:1 rel, link -> commChannel
        ownerLink.setSubject(communicationChannel);
        container.persist(ownerLink);

        return communicationChannel;
    }

    public String disableCreateCommunicationChannel(
            final CommunicationChannelOwner owner,
            final String details) {
        return communicationChannelOwnerLinks.communicationChannel(owner) != null? "Already owns a communication channel": null;
    }
    //endregion

    //region > deleteCommunicationChannel (action)
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="CommunicationChannel", sequence = "4")
    public CommunicationChannelOwner deleteCommunicationChannel(
            final CommunicationChannelOwner owner) {

        final CommunicationChannelOwnerLink ownerLink =
                communicationChannelOwnerLinks.findByPolymorphicReference(owner);
        final CommunicationChannel communicationChannel = ownerLink.getSubject();

        ownerLink.setSubject(null);
        container.flush();
        container.removeIfNotAlready(communicationChannel);
        container.removeIfNotAlready(ownerLink);

        return owner;
    }

    public String disableDeleteCommunicationChannel(final CommunicationChannelOwner owner) {
        return communicationChannelOwnerLinks.communicationChannel(owner) == null? "Does not own a communication channel": null;
    }
    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    @javax.inject.Inject
    EventBusService eventBusService;

    @javax.inject.Inject
    CommunicationChannelOwnerLinks communicationChannelOwnerLinks;

    //endregion
}
