/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.dom.impl.owner.CommunicationChannelOwner;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommunicationChannelContributions {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends
            CommChannelModule.PropertyDomainEvent<CommunicationChannelContributions, T> {
        public PropertyDomainEvent(final CommunicationChannelContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommChannelModule.CollectionDomainEvent<CommunicationChannelContributions, T> {
        public CollectionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommChannelModule.ActionDomainEvent<CommunicationChannelContributions> {
        public ActionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final CommunicationChannelContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > communicationChannels (contributed collection)

    public static class CommunicationChannelsEvent extends CollectionDomainEvent<CommunicationChannel> {
        public CommunicationChannelsEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CommunicationChannelsEvent(
                final CommunicationChannelContributions source,
                final Identifier identifier, final Of of, final CommunicationChannel value) {
            super(source, identifier, of, value);
        }
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    @Collection(
            domainEvent = CommunicationChannelsEvent.class
    )
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public SortedSet<CommunicationChannel> communicationChannels(final CommunicationChannelOwner owner) {
        return communicationChannelRepository.findByOwner(owner);
    }

    //endregion

    //region > injected services

    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    //endregion


}
