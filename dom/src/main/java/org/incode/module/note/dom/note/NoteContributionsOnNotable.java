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
package org.incode.module.note.dom.note;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.note.NoteModule;
import org.incode.module.note.dom.notable.Notable;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class NoteContributionsOnNotable {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends NoteModule.PropertyDomainEvent<NoteContributionsOnNotable, T> {
        public PropertyDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends NoteModule.CollectionDomainEvent<NoteContributionsOnNotable, T> {
        public CollectionDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends NoteModule.ActionDomainEvent<NoteContributionsOnNotable> {
        public ActionDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > notes (contributed association)

    public static class EventsDomainEvent extends ActionDomainEvent {
        public EventsDomainEvent(final NoteContributionsOnNotable source, final Identifier identifier, final Object... args) {
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
    public List<Note> notes(final Notable notable) {
        return noteRepository.findByNotable(notable);
    }
    //endregion


    //region > addNote

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    public Notable addNote(
            final Notable notable,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Notes", multiLine = NoteModule.JdoColumnLength.NUMBER_OF_LINES)
            final String note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date) {
        noteRepository.add(notable, note, date, null);
        return notable;
    }

    public boolean hideAddNote(
            final Notable notable,
            final String note,
            final LocalDate date) {
        return !hideAddNoteToCalendar(notable, note, date, null);
    }

    public LocalDate default2AddNote() {
        return clockService.now();
    }

    public String validateAddNote(
            final Notable notable,
            final String notes,
            final LocalDate date) {
        if (Strings.isNullOrEmpty(notes) && date == null) {
            return "Must specify either note text or a date (or both).";
        }
        return null;
    }

    //endregion

    //region > addNoteToCalendar

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            named = "addNote"
    )
    public Notable addNoteToCalendar(
            final Notable notable,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Note")
            final String note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Calendar")
            final String calendarName) {
        noteRepository.add(notable, note, date, calendarName);
        return notable;
    }

    public boolean hideAddNoteToCalendar(
            final Notable notable,
            final String note,
            final LocalDate date,
            final String calendarName) {
        final Collection<String> calendarNames = calendarNameRepository.calendarNamesFor(notable);
        return calendarNames == null || calendarNames.isEmpty();
    }

    public String disableAddNoteToCalendar(
            final Notable notable,
            final String note,
            final LocalDate date,
            final String calendarName) {
        return choices3AddNoteToCalendar(notable).isEmpty() ? "A note has been added to all calendars" : null;
    }

    public List<String> choices3AddNoteToCalendar(
            final Notable notable
    ) {
        final Collection<String> values = calendarNameRepository.calendarNamesFor(notable);
        if(values == null) {
            return Collections.emptyList();
        }
        final List<String> valuesCopy = Lists.newArrayList(values);
        final List<String> current = Lists.transform(
                noteRepository.findByNotable(notable),
                input -> input.getCalendarName());
        valuesCopy.removeAll(current);
        return valuesCopy;
    }

    public LocalDate default2AddNoteToCalendar() {
        return clockService.now();
    }

    public String default3AddNoteToCalendar(final Notable notable) {
        return firstOf(choices3AddNoteToCalendar(notable));
    }

    public String validateAddNoteToCalendar(
            final Notable notable,
            final String notes,
            final LocalDate date,
            final String calendarName) {
        if (Strings.isNullOrEmpty(notes) && date == null) {
            return "Must specify either note text or a date (or both).";
        }
        if (date == null && calendarName != null) {
            return "Must also specify a date if calendar has been selected";
        }
        if (date != null && calendarName == null) {
            return "Must also specify a calendar for the date";
        }
        return null;
    }

    //endregion

    //region > removeNote

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Notable removeNote(final Notable notable, final Note note) {
        noteRepository.remove(note);
        return notable;
    }

    public boolean hideRemoveNote(final Notable notable, final Note note) {
        return notable == null;
    }

    public String disableRemoveNote(final Notable notable, final Note note) {
        return choices1RemoveNote(notable).isEmpty() ? "No notes to remove" : null;
    }

    public List<Note> choices1RemoveNote(final Notable notable) {
        return notable != null ? noteRepository.findByNotable(notable): Collections.emptyList();
    }

    public Note default1RemoveNote(final Notable notable) {
        return firstOf(choices1RemoveNote(notable));
    }

    //endregion

    //region > helpers
    static <T> T firstOf(final List<T> list) {
        return list.isEmpty()? null: list.get(0);
    }
    //endregion


    //region  > (injected)
    @Inject
    NoteRepository noteRepository;

    @javax.inject.Inject
    ClockService clockService;

    @Inject
    CalendarNameRepository calendarNameRepository;
    //endregion
}
