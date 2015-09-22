/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.module.commchannel.fixture.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.util.ObjectContracts;

import org.isisaddons.module.commchannel.dom.Event;
import org.isisaddons.module.commchannel.dom.EventRepository;
import org.isisaddons.module.commchannel.dom.EventSource;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema="commchanneldemo")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@DomainObject(
        objectType = "eventdemo.EventDemoObject",
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class CommChannelDemoObject implements EventSource, Comparable<CommChannelDemoObject> {


    //region > name (property)
    
    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion


    //region > addEvent

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    public EventSource addEvent(
            @ParameterLayout(named="Calendar")
            final CalendarName calendarName,
            @ParameterLayout(named="Date")
            final LocalDate date) {
        eventRepository.newEvent(date, this, calendarName.name());
        return this;
    }
    public String disableAddEvent(
            final CalendarName calendarName, final LocalDate date) {
        return choices0AddEvent().isEmpty()? "Event added for all calendars": null;
    }
    public List<CalendarName> choices0AddEvent() {
        final CalendarName[] values = CalendarName.values();
        final ArrayList<CalendarName> calendarNames = Lists.newArrayList(Arrays.asList(values));
        final List<CalendarName> current = Lists.transform(
                eventRepository.findBySource(this),
                Functions.compose(
                        input -> CalendarName.valueOf(input),
                        Event.Functions.GET_CALENDAR_NAME));
        calendarNames.removeAll(current);
        return calendarNames;
    }
    public CalendarName default0AddEvent() {
        return firstOf(choices0AddEvent());
    }
    public LocalDate default1AddEvent() {
        return clockService.now();
    }

    //endregion

    //region > removeEvent

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    public EventSource removeEvent(final Event event) {
        eventRepository.remove(event);
        return this;
    }
    public String disableRemoveEvent(final Event event) {
        return choices0RemoveEvent().isEmpty()? "No events to remove": null;
    }
    public List<Event> choices0RemoveEvent() {
        return eventRepository.findBySource(this);
    }
    public Event default0RemoveEvent() {
        return firstOf(choices0RemoveEvent());
    }

    //endregion


    //region > eventSource impl

    /**
     * Can add to all calendars
     */
    @Programmatic
    @Override
    public Set<String> getCalendarNames() {
        return Sets.newTreeSet(
                Lists.transform(
                        Arrays.asList(CalendarName.values()),
                        input -> input.name()));
    }

    /**
     * to display in fullcalendar2
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Programmatic
    @Override
    public ImmutableMap<String, CalendarEventable> getCalendarEvents() {
        final ImmutableMap eventsByCalendarName = Maps.uniqueIndex(
                findEvents(), Event.Functions.GET_CALENDAR_NAME);
        return eventsByCalendarName;
    }

    private List<Event> findEvents() {
        return eventRepository.findBySource(this);
    }
    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "name");
    }

    @Override
    public int compareTo(final CommChannelDemoObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > helpers
    private static <T> T firstOf(final List<T> list) {
        return list.isEmpty()? null: list.get(0);
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    @SuppressWarnings("unused")
    DomainObjectContainer container;

    @javax.inject.Inject
    EventRepository eventRepository;

    @javax.inject.Inject
    ClockService clockService;

    //endregion

}
