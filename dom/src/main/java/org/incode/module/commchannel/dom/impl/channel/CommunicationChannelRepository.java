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

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CommunicationChannel.class
)
public class CommunicationChannelRepository {

    @Inject
    CommunicationChannelOwnerLinkRepository communicationChannelOwnerLinkRepository;
    @Inject
    DomainObjectContainer container;

    @Programmatic
    public CommunicationChannel findByReferenceAndType(
            final String reference, final CommunicationChannelType type) {
        return container.firstMatch(
                new QueryDefault<>(
                        CommunicationChannel.class,
                        "findByReferenceAndType",
                        "reference", reference,
                        "type", type));
    }

    @Programmatic
    public SortedSet<CommunicationChannel> findByOwner(final CommunicationChannelOwner owner) {
        final List<CommunicationChannelOwnerLink> links = communicationChannelOwnerLinkRepository.findByOwner(owner);
        return Sets.newTreeSet(
                Iterables.transform(links, CommunicationChannelOwnerLink.Functions.communicationChannel()));
    }

    @Programmatic
    public SortedSet<CommunicationChannel> findByOwnerAndType(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType type) {
        final List<CommunicationChannelOwnerLink> links =
                communicationChannelOwnerLinkRepository.findByOwnerAndCommunicationChannelType(owner, type);
        return Sets.newTreeSet(Iterables.transform(
                links, CommunicationChannelOwnerLink.Functions.communicationChannel()));
    }

    @Programmatic
    public SortedSet<CommunicationChannel> findOtherByOwnerAndType(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType type,
            final CommunicationChannel exclude) {
        final SortedSet<CommunicationChannel> communicationChannels = findByOwnerAndType(owner, type);
        communicationChannels.remove(exclude);
        return communicationChannels;
    }

}
