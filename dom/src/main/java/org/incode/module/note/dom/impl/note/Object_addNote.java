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
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.NoteModule;
import org.incode.module.note.dom.impl.calendarname.CalendarNameService;

@Mixin
public class Object_addNote {

    //region > constructor
    private final Object notable;
    public Object_addNote(final Object notable) {
        this.notable = notable;
    }

    public Object getNotable() {
        return notable;
    }
    //endregion

    //region > $$

    public static class DomainEvent extends NoteModule.ActionDomainEvent<Object_addNote> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "fa-plus",
            named = "Add"
    )
    @MemberOrder(name = "noteCollection", sequence = "1")
    public Object $$(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Note", multiLine = NoteModule.MultiLine.NOTES)
            final String note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Calendar")
            String calendarName) {
        if(date != null && calendarName == null) {
            // same defaulting logic as in the validate method.
            calendarName = choices2$$().get(0);
        }
        noteRepository.add(this.notable, note, date, calendarName);
        return this.notable;
    }

    public List<String> choices2$$() {
        final Collection<String> values = calendarNameService.calendarNamesFor(this.notable);
        final List<String> valuesCopy = Lists.newArrayList(values);
        final List<String> calendarsInUse = Lists.transform(
                noteRepository.findByNotable(this.notable),
                input -> input.getCalendarName());
        valuesCopy.removeAll(calendarsInUse);
        return valuesCopy;
    }

    public String validate$$(
            final String notes,
            final LocalDate date,
            String calendarName) {
        if (Strings.isNullOrEmpty(notes) && date == null) {
            return "Must specify either note text or a date (or both).";
        }
        if (date == null && calendarName != null) {
            return "Must also specify a date if calendar has been selected";
        }
        if (date != null && calendarName == null) {
            final List<String> calendarChoices = choices2$$();
            if(calendarChoices.size() != 1) {
                return "Must also specify a calendar for the date";
            } else {
                // don't insist on a calendar for those use cases where there is only a single calendar available.
                calendarName = calendarChoices.get(0);
            }
        }
        if(calendarName != null) {
            final Collection<String> values = calendarNameService.calendarNamesFor(this.notable);
            if(!values.contains(calendarName)) {
                return "No such calendar";
            }
            if(!choices2$$().contains(calendarName)) {
                return "This object already has a note on calendar '" + calendarName + "'";
            }
        }
        return null;
    }

    public boolean hide$$() {
        return !noteRepository.supports(this.notable);
    }

    //endregion

    //region  > (injected)
    @Inject
    NoteRepository noteRepository;

    @Inject
    CalendarNameService calendarNameService;
    //endregion


}
