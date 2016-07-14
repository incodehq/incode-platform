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

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

/**
 * Removes the {@link CommunicationChannel} from its owner, optionally specifying a replacement.
 */
@Mixin
public class CommunicationChannel_remove1 {

    //region > mixins
    private Object mixinOwner() {
        return factoryService.mixin(CommunicationChannel_owner.class, communicationChannel).$$();
    }
    //endregion

    //region > constructor
    private final CommunicationChannel<?> communicationChannel;
    public CommunicationChannel_remove1(final CommunicationChannel<?> communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    @Programmatic
    public CommunicationChannel<?> getCommunicationChannel() {
        return communicationChannel;
    }

    //endregion

    //region > $$

    public static class DomainEvent extends CommunicationChannel.ActionDomainEvent<CommunicationChannel_remove1> {
        public CommunicationChannel<?> getReplacement() {
            return (CommunicationChannel<?>) getArguments().get(0);
        }
    }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            cssClass = "btn-danger",
            named = "Remove"
    )
    public Object $$(
            @ParameterLayout(named = "Replace with")
            @Nullable
            final CommunicationChannel replacement) {
        final Object owner = mixinOwner();
        removeLink();
        return owner;
    }

    public boolean hide$$() {
        return choices0$$().isEmpty();
    }

    public SortedSet<CommunicationChannel> choices0$$() {
        return communicationChannelRepository.findOtherByOwnerAndType(
                mixinOwner(), this.communicationChannel.getType(),
                this.communicationChannel);
    }

    void removeLink() {
        ownerLinkRepository.removeOwnerLink(communicationChannel);
        repositoryService.remove(communicationChannel);
    }
    //endregion


    //region > injected services
    @Inject
    CommunicationChannelOwnerLinkRepository ownerLinkRepository;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    @Inject
    RepositoryService repositoryService;
    @Inject
    FactoryService factoryService;
    //endregion

}
