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

import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.NonRecoverableException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.EventBusService;

@DomainService(repositoryFor = CommunicationChannel.class)
@DomainServiceLayout(menuOrder = "10")
public class CommunicationChannels {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<CommunicationChannel> listAll() {
        return container.allInstances(CommunicationChannel.class);
    }
    //endregion

    //region > add (programmatic)
    @Programmatic
    public CommunicationChannel add(
            final String details,
            final CommunicationChannelOwner owner) {
        final CommunicationChannel communicationChannel = container.newTransientInstance(CommunicationChannel.class);
        communicationChannel.setDetails(details);

        final CommunicationChannelOwnerLink.InstantiateEvent event = new CommunicationChannelOwnerLink.InstantiateEvent(this, communicationChannel, owner);
        eventBusService.post(event);

        final Class<? extends CommunicationChannelOwnerLink> subtype = event.getSubtype();
        if(subtype == null) {
            throw new NonRecoverableException("Cannot create link to " + container.titleOf(owner) + ", no subtype provided");
        }

        final CommunicationChannelOwnerLink ownerLink = container.newTransientInstance(subtype);
        ownerLink.setTo(owner);

        // persist the link
        container.persist(ownerLink);
        container.flush();

        // first half of the 1:1 rel, commChannel -> link
        communicationChannel.setOwnerLink(ownerLink);
        container.persist(communicationChannel);

        // second half of the 1:1 rel, link -> commChannel
        ownerLink.setFrom(communicationChannel);

        return communicationChannel;
    }
    //endregion

    //region > add (programmatic)
    @Programmatic
    public void remove(final CommunicationChannel communicationChannel) {
        final CommunicationChannelOwnerLink ownerLink = communicationChannel.getOwnerLink();
        ownerLink.setFrom(null);
        container.flush();
        container.removeIfNotAlready(communicationChannel);
        container.removeIfNotAlready(ownerLink);
    }
    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    @javax.inject.Inject
    private EventBusService eventBusService;


    //endregion
}
