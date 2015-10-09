/*
 *  Copyright 2015 incode.org
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

import java.util.SortedSet;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannelActionRemove {

    @Inject
    CommunicationChannelDerivedOwner communicationChannelDerivedOwner;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    @Inject
    DomainObjectContainer container;


    public static class RemoveEvent
            extends CommunicationChannel.ActionDomainEvent<CommunicationChannelActionRemove> { }
    @Action(
            domainEvent = RemoveEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public CommunicationChannelOwner remove(
            final CommunicationChannel communicationChannel,
            @ParameterLayout(named = "Replace with")
            @Parameter(optionality = Optionality.OPTIONAL)
            final CommunicationChannel replacement) {
        final CommunicationChannelOwner owner = communicationChannelDerivedOwner.owner(communicationChannel);
        communicationChannelDerivedOwner.removeOwnerLink(communicationChannel);
        container.remove(this);
        return owner;
    }

    public SortedSet<CommunicationChannel> choices1Remove(
            final CommunicationChannel communicationChannel
    ) {
        return communicationChannelRepository.findOtherByOwnerAndType(
                communicationChannelDerivedOwner.owner(communicationChannel), communicationChannel.getType(), communicationChannel);
    }



}
