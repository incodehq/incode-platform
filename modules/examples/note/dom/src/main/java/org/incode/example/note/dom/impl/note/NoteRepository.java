package org.incode.example.note.dom.impl.note;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.example.note.dom.impl.notablelink.NotableLink;
import org.incode.example.note.dom.impl.notablelink.NotableLinkRepository;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Note.class
)
public class NoteRepository {

    //region > findByNotable (programmatic)
    @Programmatic
    public List<Note> findByNotable(final Object notable) {
        final List<NotableLink> links = linkRepository.findByNotable(notable);
        return Lists.newArrayList(
                Iterables.transform(links, NotableLink.Functions.note()));
    }
    //endregion

    //region > findByNotableAndCalendarName (programmatic)
    @Programmatic
    public Note findByNotableAndCalendarName(
            final Object notable,
            final String calendarName) {
        final NotableLink link = linkRepository
                .findByNotableAndCalendarName(notable, calendarName);
        return NotableLink.Functions.note().apply(link);
    }
    //endregion

    //region > findInDateRange (programmatic)
    @Programmatic
    public List<Note> findInDateRange(
            final LocalDate startDate,
            final LocalDate endDate) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Note.class,
                        "findInDateRange",
                        "startDate", startDate,
                        "endDate", endDate));
    }
    //endregion

    //region > findByNotableInDateRange (programmatic)
    @Programmatic
    public Iterable<Note> findByNotableInDateRange(
            final Object notable,
            final LocalDate startDate,
            final LocalDate endDate) {
        final List<NotableLink> link = linkRepository
                .findByNotableInDateRange(notable, startDate, endDate);
        return Iterables.transform(link, NotableLink.Functions.note());
    }
    //endregion

    //region > add (programmatic)

    @Programmatic
    public Note add(
            final Object notable,
            final String noteText,
            final LocalDate date,
            final String calendarName) {
        final Note note = repositoryService.instantiate(Note.class);
        note.setDate(date);
        note.setCalendarName(calendarName);
        note.setContent(noteText);
        repositoryService.persistAndFlush(note);

        final NotableLink link = notableLinkRepository.createLink(note, notable);
        // stored redundantly for querying...
        link.setCalendarName(calendarName);
        link.setDate(date);

        return note;
    }

    //endregion

    //region > remove (programmatic)
    @Programmatic
    public void remove(Note note) {
        final NotableLink link = linkRepository.findByNote(note);
        repositoryService.removeAndFlush(link);
        repositoryService.removeAndFlush(note);
    }
    //endregion

    //region > allNotes (programmatic)

    @Programmatic
    public List<Note> allNotes() {
        return repositoryService.allInstances(Note.class);
    }
    //endregion

    //region > injected
    @Inject
    NotableLinkRepository notableLinkRepository;
    @Inject
    NotableLinkRepository linkRepository;
    @Inject
    RepositoryService repositoryService;
    //endregion

}
