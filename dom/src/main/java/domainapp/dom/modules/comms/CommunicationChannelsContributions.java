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

import domainapp.dom.modules.poly.PolymorphicLinkHelper;
import domainapp.dom.modules.poly.PolymorphicLinkInstantiateEvent;

import javax.annotation.PostConstruct;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
@DomainServiceLayout(menuOrder = "10")
public class CommunicationChannelsContributions {

    //region > init
    public static class CommunicationChannelOwnerLinkInstantiateEvent
            extends PolymorphicLinkInstantiateEvent<CommunicationChannel, CommunicationChannelOwner, CommunicationChannelOwnerLink> {

        public CommunicationChannelOwnerLinkInstantiateEvent(final Object source, final CommunicationChannel subject, final CommunicationChannelOwner owner) {
            super(CommunicationChannelOwnerLink.class, source, subject, owner);
        }
    }

    PolymorphicLinkHelper<CommunicationChannel,CommunicationChannelOwner,CommunicationChannelOwnerLink,CommunicationChannelOwnerLinkInstantiateEvent> ownerLinkHelper;

    @PostConstruct
    public void init() {
        ownerLinkHelper = container.injectServicesInto(
                new PolymorphicLinkHelper<>(
                        this,
                        CommunicationChannel.class,
                        CommunicationChannelOwner.class,
                        CommunicationChannelOwnerLink.class,
                        CommunicationChannelOwnerLinkInstantiateEvent.class
                ));

    }
    //endregion

    //region > createCommunicationChannel (contributed action)

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
        container.persist(communicationChannel);
        container.flush();

        ownerLinkHelper.createLink(communicationChannel, owner);
        return communicationChannel;
    }

    public String disableCreateCommunicationChannel(
            final CommunicationChannelOwner owner,
            final String details) {
        return communicationChannel(owner) != null? "Already owns a communication channel": null;
    }

    public String validateCreateCommunicationChannel(final CommunicationChannelOwner owner, final String details) {
        return details.contains("!")? "No exclamation marks allowed in details": null;
    }
    //endregion

    //region > deleteCommunicationChannel (contributed action)
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="CommunicationChannel", sequence = "4")
    public CommunicationChannelOwner deleteCommunicationChannel(
            final CommunicationChannelOwner owner) {

        final CommunicationChannelOwnerLink ownerLink =
                communicationChannelOwnerLinks.findByPolymorphicReference(owner);
        final CommunicationChannel communicationChannel = ownerLink.getSubject();

        container.removeIfNotAlready(ownerLink);
        container.removeIfNotAlready(communicationChannel);

        return owner;
    }

    public String disableDeleteCommunicationChannel(final CommunicationChannelOwner owner) {
        return communicationChannel(owner) == null? "Does not own a communication channel": null;
    }
    //endregion

    //region > owner (contributed derived property)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public CommunicationChannelOwner owner(final CommunicationChannel communicationChannel) {
        final CommunicationChannelOwnerLink ownerLink = communicationChannelOwnerLinks.findBySubject(communicationChannel);
        return ownerLink != null? ownerLink.getPolymorphicReference(): null;
    }

    //endregion

    //region > communicationChannel (contributed derived property)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public CommunicationChannel communicationChannel(final CommunicationChannelOwner communicationChannelOwner) {
        final CommunicationChannelOwnerLink link = communicationChannelOwnerLinks.findByPolymorphicReference(communicationChannelOwner);
        return link != null? link.getSubject(): null;
    }
    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    @javax.inject.Inject
    CommunicationChannelOwnerLinks communicationChannelOwnerLinks;
    //endregion

}
