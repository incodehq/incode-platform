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
package org.isisaddons.module.event.dom;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.event.EventModule;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class EventContributions {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends EventModule.PropertyDomainEvent<EventContributions, T> {
        public PropertyDomainEvent(final EventContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final EventContributions source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends EventModule.CollectionDomainEvent<EventContributions, T> {
        public CollectionDomainEvent(final EventContributions source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final EventContributions source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends EventModule.ActionDomainEvent<EventContributions> {
        public ActionDomainEvent(final EventContributions source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final EventContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final EventContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > events (contributed association)

    public static class EventsDomainEvent extends ActionDomainEvent {
        public EventsDomainEvent(final EventContributions source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = EventsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<Event> events(final EventSource eventSource) {
        return events.findBySource(eventSource);
    }
    //endregion


    //region > injected
    @Inject
    EventRepository events;
    //endregion
}
