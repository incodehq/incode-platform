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
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

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


    public static class DomainEvent extends Note.ActionDomainEvent<Note_changeDate> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(
        named = "Change"
    )
    @MemberOrder(name = "calendarName", sequence = "1")
    public Note $$(
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

    public Collection<String> choices1$$() {
        final Collection<String> values = calendarNameService.calendarNamesFor(this.note.getNotable());
        final List<String> valuesCopy = Lists.newArrayList(values);
        final List<String> currentCalendarsInUse = Lists.transform(
                noteRepository.findByNotable(this.note.getNotable()),
                Note::getCalendarName);
        valuesCopy.removeAll(currentCalendarsInUse);
        if(this.note.getCalendarName() != null) {
            valuesCopy.add(this.note.getCalendarName()); // add back in current for this note's notable
        }
        return valuesCopy;
    }

    public LocalDate default0$$() {
        return this.note.getDate();
    }

    public String default1$$() {
        return this.note.getCalendarName();
    }

    public String validate$$(final LocalDate date, final String calendarName) {
        if( date != null && calendarName != null) {
            return null;
        }
        if( date == null && calendarName == null && !Strings.isNullOrEmpty(this.note.getNotes())) {
            return null; // can have a note with just text (no date/calendar)
        }
        return "Must specify either note text or a date/calendar (or both).";
    }
    public String disable$$() {
        // otherwise, must be at least one calendar to select
        if(choices1$$().isEmpty()) {
            return "Notes already associated with all calendars";
        }
        return null;
    }


}
