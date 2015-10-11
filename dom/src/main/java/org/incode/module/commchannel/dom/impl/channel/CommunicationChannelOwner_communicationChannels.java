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

import java.util.SortedSet;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannelOwner_communicationChannels {

    @Inject
    CommunicationChannelRepository communicationChannelRepository;


    public static class CommunicationChannelsEvent
            extends CommunicationChannelOwner.CollectionDomainEvent<CommunicationChannelOwner_communicationChannels, CommunicationChannel> { }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    @Collection(
            domainEvent = CommunicationChannelsEvent.class
    )
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public SortedSet<CommunicationChannel> communicationChannels(final CommunicationChannelOwner owner) {
        return communicationChannelRepository.findByOwner(owner);
    }

}
