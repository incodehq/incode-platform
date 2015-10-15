/*
 *
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
package org.incode.module.note.dom.impl.note;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.NoteModule;
import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.calendarname.CalendarNameService;

@Mixin
public class Notable_addNote {

    //region  > (injected)
    @Inject
    NoteRepository noteRepository;

    @Inject
    CalendarNameService calendarNameService;
    //endregion

    //region > constructor
    private final Notable notable;
    public Notable_addNote(final Notable notable) {
        this.notable = notable;
    }

    public Notable getNotable() {
        return notable;
    }
    //endregion


    public static class Event extends Notable.ActionDomainEvent<Notable_addNote> { }

    @Action(
            domainEvent = Event.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    public Notable __(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Note", multiLine = NoteModule.MultiLine.NOTES)
            final String note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Calendar")
            final String calendarName) {
        noteRepository.add(this.notable, note, date, calendarName);
        return this.notable;
    }

    public List<String> choices2__(
    ) {
        final Collection<String> values = calendarNameService.calendarNamesFor(this.notable);
        final List<String> valuesCopy = Lists.newArrayList(values);
        final List<String> calendarsInUse = Lists.transform(
                noteRepository.findByNotable(this.notable),
                input -> input.getCalendarName());
        valuesCopy.removeAll(calendarsInUse);
        return valuesCopy;
    }

    public String validate__(
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
        if(calendarName != null) {
            final Collection<String> values = calendarNameService.calendarNamesFor(this.notable);
            if(!values.contains(calendarName)) {
                return "No such calendar";
            }
            if(!choices2__().contains(calendarName)) {
                return "This object already has a note on calendar '" + calendarName + "'";
            }
        }
        return null;
    }
    //endregion

}
