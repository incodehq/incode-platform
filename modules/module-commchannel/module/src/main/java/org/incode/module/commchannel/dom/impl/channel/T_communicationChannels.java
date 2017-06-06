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
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.CommChannelModule;

public abstract class T_communicationChannels<T> {

    //region > constructor
    private final T communicationChannelOwner;
    public T_communicationChannels(final T communicationChannelOwner) {
        this.communicationChannelOwner = communicationChannelOwner;
    }
    //endregion

    //region > $$
    public static class DomainEvent extends CommChannelModule.CollectionDomainEvent
                                        <T_communicationChannels, CommunicationChannel> { }
    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(
            named = "Communication Channels", // regression in isis 1.11.x requires this to be specified
            defaultView = "table"
    )
    @Collection(
            domainEvent = DomainEvent.class
    )
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public SortedSet<CommunicationChannel> $$() {
        return communicationChannelRepository.findByOwner(communicationChannelOwner);
    }

    //endregion

    //region > injected services
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    //endregion


}
