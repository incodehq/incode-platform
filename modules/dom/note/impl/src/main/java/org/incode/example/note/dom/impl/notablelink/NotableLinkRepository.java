package org.incode.example.note.dom.impl.notablelink;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import org.incode.example.note.dom.impl.note.Note;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = NotableLink.class
)
public class NotableLinkRepository {

    //region > findByNote (programmatic)
    @Programmatic
    public NotableLink findByNote(final Note note) {
        return repositoryService.firstMatch(
                new QueryDefault<>(NotableLink.class,
                        "findByNote",
                        "note", note));
    }
    //endregion

    //region > findByNotable (programmatic)
    @Programmatic
    public List<NotableLink> findByNotable(final Object notable) {
        if(notable == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(notable);
        if(bookmark == null) {
            return null;
        }
        final String notableStr = bookmark.toString();
        return repositoryService.allMatches(
                new QueryDefault<>(NotableLink.class,
                        "findByNotable",
                        "notableStr", notableStr));
    }
    //endregion

    //region > findByNotableAndCalendarName (programmatic)

    /**
     * Each notable can only have one note per calendar, thus this method returns a single object rather than a list.
     */
    @Programmatic
    public NotableLink findByNotableAndCalendarName(
            final Object notable,
            final String calendarName) {
        if(notable == null) {
            return null;
        }
        if(calendarName == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(notable);
        if(bookmark == null) {
            return null;
        }
        final String notableStr = bookmark.toString();
        return repositoryService.firstMatch(
                new QueryDefault<>(NotableLink.class,
                        "findByNotableAndCalendarName",
                        "notableStr", notableStr,
                        "calendarName", calendarName));
    }
    //endregion

    //region > findByNotableInDateRange (programmatic)
    @Programmatic
    public List<NotableLink> findByNotableInDateRange(
            final Object notable,
            final LocalDate startDate,
            final LocalDate endDate) {
        if(notable == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(notable);
        if(bookmark == null) {
            return null;
        }
        if(startDate == null) {
            return null;
        }
        if(endDate == null) {
            return null;
        }
        final String notableStr = bookmark.toString();
        return repositoryService.allMatches(
                new QueryDefault<>(NotableLink.class,
                        "findByNotableInDateRange",
                        "notableStr", notableStr,
                        "startDate", startDate,
                        "endDate", endDate));
    }
    //endregion

    //region > createLink (programmatic)
    @Programmatic
    public NotableLink createLink(
            final Note note,
            final Object notable) {

        final Class<? extends NotableLink> subtype = subtypeClassFor(notable);

        final NotableLink link = repositoryService.instantiate(subtype);

        link.setNote(note);

        final Bookmark bookmark = bookmarkService.bookmarkFor(notable);
        link.setNotable(notable);
        link.setNotableStr(bookmark.toString());

        repositoryService.persistAndFlush(link);

        return link;
    }

    private Class<? extends NotableLink> subtypeClassFor(
            final Object classified) {
        Class<?> domainClass = classified.getClass();
        for (SubtypeProvider subtypeProvider : subtypeProviders) {
            Class<? extends NotableLink> subtype = subtypeProvider.subtypeFor(domainClass);
            if(subtype != null) {
                return subtype;
            }
        }
        throw new IllegalStateException(String.format(
                "No subtype of NotableLink was found for '%s'; implement the NotableLinkRepository.SubtypeProvider SPI",
                domainClass.getName()));
    }
    //endregion


    //endregion

    //region > updateLink
    @Programmatic
    public void updateLink(final Note note) {
        final NotableLink link = findByNote(note);
        sync(note, link);
    }
    //endregion

    //region > helpers (sync)

    /**
     * copy over details from the {@link Note#} to the {@link NotableLink} (derived propoerties to support querying).
     */
    void sync(final Note note, final NotableLink link) {
        if(link == null) {
            return;
        }
        link.setDate(note.getDate());
        link.setCalendarName(note.getCalendarName());
    }
    //endregion

    //region > SubtypeProvider SPI

    /**
     * SPI to be implemented (as a {@link DomainService}) for any domain object to which {@link NotableLink}s can be
     * attached.
     */
    public interface SubtypeProvider {
        /**
         * @return the subtype of {@link NotableLink} to use to hold the (type-safe) link of the domain object
         */
        @Programmatic
        Class<? extends NotableLink> subtypeFor(Class<?> domainObject);
    }
    /**
     * Convenience adapter to help implement the {@link SubtypeProvider} SPI; simply returns the class pair passed into constructor.
     */
    public abstract static class SubtypeProviderAbstract implements SubtypeProvider {
        private final Class<?> notableDomainType;
        private final Class<? extends NotableLink> notableLinkSubtype;

        protected SubtypeProviderAbstract(final Class<?> notableDomainType, final Class<? extends NotableLink> notableLinkSubtype) {
            this.notableDomainType = notableDomainType;
            this.notableLinkSubtype = notableLinkSubtype;
        }
        @Override
        public Class<? extends NotableLink> subtypeFor(final Class<?> candidateNotableDomainType) {
            return notableDomainType.isAssignableFrom(candidateNotableDomainType) ? notableLinkSubtype : null;
        }
    }

    //endregion


    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;

    @javax.inject.Inject
    BookmarkService bookmarkService;

    @Inject
    List<SubtypeProvider> subtypeProviders;
    //endregion

}
