package org.incode.module.note.dom.impl.note;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.note.dom.NoteModule;
import org.incode.module.note.dom.impl.calendarname.CalendarNameService;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class NoteActionChangeDate {

    public static class DomainEvent extends NoteModule.ActionDomainEvent<NoteActionChangeDate> {
        public DomainEvent(
                final NoteActionChangeDate source,
                final Identifier identifier,
                final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Note changeDate(
            final Note note,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Calendar")
            final String calendarName) {
        note.setDate(date);
        note.setCalendarName(calendarName);
        notableLinkRepository.updateLink(note);
        return note;
    }

    public Collection<String> choices2ChangeDate(final Note note) {
        final Collection<String> values = calendarNameService.calendarNamesFor(note.getNotable());
        final List<String> valuesCopy = Lists.newArrayList(values);
        final List<String> currentCalendarsInUse = Lists.transform(
                noteRepository.findByNotable(note.getNotable()),
                Note::getCalendarName);
        valuesCopy.removeAll(currentCalendarsInUse);
        valuesCopy.add(note.getCalendarName()); // add back in current for this note's notable
        return valuesCopy;
    }

    public LocalDate default1ChangeDate(final Note note) {
        return note.getDate();
    }

    public String default2ChangeDate(final Note note) {
        return note.getCalendarName();
    }

    public String validateChangeDate(final Note note, final LocalDate date, final String calendarName) {
        if(Strings.isNullOrEmpty(note.getNotes()) && (date == null || calendarName == null)) {
            return "Must specify either note text or a date/calendar (or both).";
        }
        return null;
    }

    @Inject
    CalendarNameService calendarNameService;
    @Inject
    NoteRepository noteRepository;
    @Inject
    NotableLinkRepository notableLinkRepository;

}
