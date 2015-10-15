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
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.NoteModule;
import org.incode.module.note.dom.impl.calendarname.CalendarNameService;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;

@Mixin
public class Note_changeDate {

    //region > injected services
    @Inject
    CalendarNameService calendarNameService;
    @Inject
    NoteRepository noteRepository;
    @Inject
    NotableLinkRepository notableLinkRepository;
    //endregion

    //region > constructor
    private final Note note;
    public Note_changeDate(final Note note) {
        this.note = note;
    }
    @Programmatic
    public Note getNote() {
        return note;
    }
    //endregion


    public static class Event extends NoteModule.ActionDomainEvent<Note_changeDate> { }

    @Action(
            domainEvent = Event.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Note __(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Calendar")
            final String calendarName) {
        this.note.setDate(date);
        this.note.setCalendarName(calendarName);
        notableLinkRepository.updateLink(this.note);
        return this.note;
    }

    public Collection<String> choices1__() {
        final Collection<String> values = calendarNameService.calendarNamesFor(this.note.getNotable());
        final List<String> valuesCopy = Lists.newArrayList(values);
        final List<String> currentCalendarsInUse = Lists.transform(
                noteRepository.findByNotable(this.note.getNotable()),
                Note::getCalendarName);
        valuesCopy.removeAll(currentCalendarsInUse);
        valuesCopy.add(this.note.getCalendarName()); // add back in current for this note's notable
        return valuesCopy;
    }

    public LocalDate default0__() {
        return this.note.getDate();
    }

    public String default1__() {
        return this.note.getCalendarName();
    }

    public String validate__(final LocalDate date, final String calendarName) {
        if(Strings.isNullOrEmpty(this.note.getNotes()) && (date == null || calendarName == null)) {
            return "Must specify either note text or a date/calendar (or both).";
        }
        return null;
    }


}
