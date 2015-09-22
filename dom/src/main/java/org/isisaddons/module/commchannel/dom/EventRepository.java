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
package org.isisaddons.module.commchannel.dom;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Event.class
)
public class EventRepository {

    //region > findBySource (programmatic)
    @Programmatic
    public List<Event> findBySource(final EventSource eventSource) {
        final List<EventSourceLink> links = eventSourceLinkRepository.findBySource(eventSource);
        return Lists.newArrayList(
                Iterables.transform(links, EventSourceLink.Functions.event()));
    }
    //endregion

    //region > findBySourceAndCalendarName (programmatic)
    @Programmatic
    public Event findBySourceAndCalendarName(
            final EventSource eventSource,
            final String calendarName) {
        final EventSourceLink link = eventSourceLinkRepository.findBySourceAndCalendarName(eventSource, calendarName);
        return link != null? link.getEvent(): null;
    }
    //endregion

    //region > newEvent (programmatic)
    @Programmatic
    public Event newEvent(
            final LocalDate date, final EventSource eventSource, final String calendarName) {
        final Event event = container.newTransientInstance(Event.class);
        event.setDate(date);
        event.setCalendarName(calendarName);
        event.setSource(eventSource);
        container.persistIfNotAlready(event);

        return event;
    }
    //endregion

    //region > remove (programmatic)
    @Programmatic
    public void remove(Event event) {
        final EventSourceLink link = eventSourceLinkRepository.findByEvent(event);
        container.removeIfNotAlready(link);
        container.flush();
        container.removeIfNotAlready(event);
        container.flush();
    }
    //endregion

    //region > findEventsInDateRange (programmatic)
    @Programmatic
    public List<Event> findEventsInDateRange(final LocalDate rangeStartDate, final LocalDate rangeEndDate) {
        return container.allMatches(
                new QueryDefault<>(
                        Event.class,
                        "findInDateRange",
                        "rangeStartDate", rangeStartDate,
                        "rangeEndDate", rangeEndDate));
    }
    //endregion

    //region > allEvents (programmatic)

    @Programmatic
    public List<Event> allEvents() {
        return container.allInstances(Event.class);
    }
    //endregion

    //region > injected
    @Inject
    EventSourceLinkRepository eventSourceLinkRepository;
    @Inject
    DomainObjectContainer container;
    //endregion

}
