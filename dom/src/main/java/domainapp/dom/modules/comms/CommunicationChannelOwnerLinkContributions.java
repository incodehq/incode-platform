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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannelOwnerLinkContributions {

    //region > add (action)
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="CommunicationChannel",sequence = "3")
    public CommunicationChannel add(
            final CommunicationChannelOwner owner,
            @ParameterLayout(named = "Details")
            final String details) {

        return communicationChannels.add(details, owner);
    }

    public String disableAdd(final CommunicationChannelOwner owner,
                             final String details) {
        return communicationChannel(owner) != null? "Already owns a communication channel": null;
    }
    //endregion

    //region > remove (action)
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="CommunicationChannel", sequence = "4")
    public CommunicationChannelOwner remove(
            final CommunicationChannelOwner owner) {

        final CommunicationChannel communicationChannel = communicationChannel(owner);
        communicationChannels.remove(communicationChannel);

        return owner;
    }
    public String disableRemove(final CommunicationChannelOwner owner) {
        return communicationChannel(owner) == null? "Does not own a communication channel": null;
    }
    //endregion

    //region > owner (derived property)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public CommunicationChannelOwner owner(final CommunicationChannel communicationChannel) {
        return communicationChannel.getOwnerLink() != null? communicationChannel.getOwnerLink().getTo(): null;
    }

    //endregion

    //region > communicationChannel (derived property)
    private CommunicationChannel communicationChannel;

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public CommunicationChannel communicationChannel(final CommunicationChannelOwner communicationChannelOwner) {
        final CommunicationChannelOwnerLink link = communicationChannelOwnerLinks.findByTo(communicationChannelOwner);
        return link != null? link.getFrom(): null;
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private CommunicationChannels communicationChannels;

    @javax.inject.Inject
    private CommunicationChannelOwnerLinks communicationChannelOwnerLinks;

    //endregion
}
