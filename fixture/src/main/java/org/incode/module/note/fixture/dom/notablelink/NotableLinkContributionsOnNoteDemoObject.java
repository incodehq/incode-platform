package org.incode.module.note.fixture.dom.notablelink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Functions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.note.dom.notable.Notable;
import org.incode.module.note.dom.note.Note;
import org.incode.module.note.dom.note.NoteRepository;
import org.incode.module.note.fixture.dom.CalendarName;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class NotableLinkContributionsOnNoteDemoObject {

    //region > addNote

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    public Notable addNote(
            final Notable notable,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Note")
            final String note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Calendar")
            final CalendarName calendarName) {
        noteRepository.add(notable, note, date, calendarName.name());
        return notable;
    }

    public String disableAddNote(
            final Notable notable,
            final String note,
            final LocalDate date,
            final CalendarName calendarName) {
        return choices3AddNote(notable).isEmpty() ? "A note has been added to all calendars" : null;
    }

    public List<CalendarName> choices3AddNote(
            final Notable notable
    ) {
        final CalendarName[] values = CalendarName.values();
        final ArrayList<CalendarName> calendarNames = Lists.newArrayList(Arrays.asList(values));
        final List<CalendarName> current = Lists.transform(
                noteRepository.findByNotable(notable),
                Functions.compose(
                        input -> CalendarName.valueOf(input),
                        Note.Functions.GET_CALENDAR_NAME));
        calendarNames.removeAll(current);
        return calendarNames;
    }

    public LocalDate default2AddNote() {
        return clockService.now();
    }

    public CalendarName default3AddNote(final Notable notable) {
        return firstOf(choices3AddNote(notable));
    }

    public String validateAddNote(
            final Notable notable,
            final String notes,
            final LocalDate date,
            final CalendarName calendarName) {
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

    public String disableRemoveNote(final Notable notable, final Note note) {
        return choices1RemoveNote(notable).isEmpty() ? "No notes to remove" : null;
    }

    public List<Note> choices1RemoveNote(final Notable notable) {
        return noteRepository.findByNotable(notable);
    }

    public Note default1RemoveNote(final Notable notable) {
        return firstOf(choices1RemoveNote(null));
    }

    //endregion

    //region > helpers
    private static <T> T firstOf(final List<T> list) {
        return list.isEmpty()? null: list.get(0);
    }
    //endregion


    //region  > (injected)
    @Inject
    NoteRepository noteRepository;

    @javax.inject.Inject
    ClockService clockService;

    //endregion

}
