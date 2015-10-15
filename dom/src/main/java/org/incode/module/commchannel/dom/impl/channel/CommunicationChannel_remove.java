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
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.api.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@Mixin
public class CommunicationChannel_remove {

    //region > injected services
    @Inject
    CommunicationChannelOwnerLinkRepository ownerLinkRepository;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    @Inject
    DomainObjectContainer container;
    //endregion

    //region > mixins
    private CommunicationChannelOwner mixinOwner() {
        return container.mixin(CommunicationChannel_owner.class, communicationChannel).__();
    }
    //endregion

    //region > constructor
    private final CommunicationChannel<?> communicationChannel;
    public CommunicationChannel_remove(final CommunicationChannel<?> communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    @Programmatic
    public CommunicationChannel<?> getCommunicationChannel() {
        return communicationChannel;
    }

    //endregion

    public static class Event extends CommunicationChannel.ActionDomainEvent<CommunicationChannel_remove> {
        public CommunicationChannel<?> getReplacement() {
            return (CommunicationChannel<?>) getArguments().get(0);
        }
    }

    @Action(
            domainEvent = Event.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public CommunicationChannelOwner __(
            @ParameterLayout(named = "Replace with")
            @Parameter(optionality = Optionality.OPTIONAL)
            final CommunicationChannel replacement) {
        final CommunicationChannelOwner owner = mixinOwner();
        removeLink();
        return owner;
    }

    public SortedSet<CommunicationChannel> choices0__() {
        return communicationChannelRepository.findOtherByOwnerAndType(
                mixinOwner(), this.communicationChannel.getType(),
                this.communicationChannel);
    }

    private void removeLink() {
        ownerLinkRepository.removeOwnerLink(communicationChannel);
        container.remove(communicationChannel);
    }

}
