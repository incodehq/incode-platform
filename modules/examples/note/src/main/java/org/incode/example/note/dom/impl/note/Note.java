package org.incode.example.note.dom.impl.note;

import java.util.Locale;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Function;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.LocaleProvider;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;

import org.incode.example.note.NoteModule;
import org.incode.example.note.dom.impl.notablelink.NotableLink;
import org.incode.example.note.dom.impl.notablelink.NotableLinkRepository;

import lombok.Getter;
import lombok.Setter;

/**
 * An note that has or is scheduled to occur at some point in time, pertaining
 * to a "notable" domain object.
 */
@javax.jdo.annotations.PersistenceCapable(
        schema = "incodeNote",
        table = "Note",
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findInDateRange", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.example.note.dom.impl.note.Note "
                        + "WHERE date >= :startDate "
                        + "   && date <= :endDate")
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "Note_date_idx",
                members = { "date" })
})
@DomainObject(
        editing = Editing.DISABLED
)
public class Note implements CalendarEventable, Comparable<Note> {

    private static final int NOTES_ABBREVIATED_TO = 40;

    //region > event classes
    public static abstract class PropertyDomainEvent<S,T> extends NoteModule.PropertyDomainEvent<S, T> { }
    public static abstract class CollectionDomainEvent<S,T> extends NoteModule.CollectionDomainEvent<S, T> { }
    public static abstract class ActionDomainEvent<S> extends NoteModule.ActionDomainEvent<S> { }
    //endregion

    //region > title
    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        // 1.13.0 refactoring, issue with persisting note, trying to find the cause...
        // buf.append(titleService.titleOf(getNotable()));
        if(getDate() != null) {
            // final String dateStr = titleService.titleOf(getDate()); // broken in isis 1.9.0
            final Locale locale = localeProvider.getLocale();
            final String dateStr = DateTimeFormat.forStyle("M-").withLocale(locale).print(getDate());
            buf.append(" @").append(dateStr);
        }
        buf.append(": ").append(getAbbreviated());
        return buf.toString();
    }

    //endregion

    public static class ContentDomainEvent extends PropertyDomainEvent<Note,String> { }
    /**
     * Hidden in tables, instead the derived {@link #getAbbreviated()} is shown.
     */
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true", length = NoteModule.JdoColumnLength.NOTES)
    @Property(
            domainEvent = ContentDomainEvent.class,
            hidden = Where.ALL_TABLES
    )
    private String content;


    public static class DateDomainEvent extends PropertyDomainEvent<Note,LocalDate> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(
            domainEvent = DateDomainEvent.class
    )
    private LocalDate date;


    public static class CalendarNameDomainEvent extends PropertyDomainEvent<Note,String> { }
    /**
     * The name of the &quot;calendar&quot; (if any) to which this note belongs.
     *
     * <p>
     * The &quot;calendar&quot; is a string identifier that indicates the nature
     * of a note.  These are expected to be uniquely identifiable for all and
     * any content that might be created. They therefore typically
     * include information relating to the type/class of the note's
     * {@link #getNotable() subject}.
     *
     * <p>
     * For example, a note on a lease's
     * <tt>FixedBreakOption</tt> has three dates: the <i>break date</i>, the
     * <i>exercise date</i> and the <i>reminder date</i>. These therefore
     * correspond to three different calendar names, respectively <i>Fixed
     * break</i>, <i>Fixed break exercise</i> and <i>Fixed break exercise
     * reminder</i>.
     */
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true", length = NoteModule.JdoColumnLength.CALENDAR_NAME)
    @Property(
            domainEvent = CalendarNameDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String calendarName;


    //region > notable (derived property)

    public static class NotableDomainEvent extends PropertyDomainEvent<Note,Object> { }

    /**
     * Polymorphic association to (any implementation of) "notable" domain object.
     */
    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = NotableDomainEvent.class,
            editing = Editing.DISABLED,
            hidden = Where.PARENTED_TABLES,
            notPersisted = true
    )
    public Object getNotable() {
        final NotableLink link = getNotableLink();
        return link != null? link.getNotable(): null;
    }

    @javax.jdo.annotations.NotPersistent
    @Programmatic
    private NotableLink getNotableLink() {
        if (!repositoryService.isPersistent(this)) {
            return null;
        }
        return notableLinkRepository.findByNote(this);
    }
    //endregion

    //region > notesAbbreviated (derived property)

    public static class NotesAbbreviatedDomainEvent extends PropertyDomainEvent<Note,String> { }

    /**
     * Derived from {@link #getContent()}, solely for use in title and in tables.
     */
    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = NotesAbbreviatedDomainEvent.class,
            hidden = Where.OBJECT_FORMS
    )
    public String getAbbreviated() {
        return trim(getContent(), "...", NOTES_ABBREVIATED_TO);
    }

    static String trim(final String notes, final String ending, final int length) {
        if(notes == null || notes.length() <= length) {
            return notes;
        }
        return notes.substring(0, length-ending.length()) + ending ;
    }
    //endregion

    //region > CalendarEventable impl
    @Programmatic
    public CalendarEvent toCalendarEvent() {
        if(getDate() == null || getCalendarName() == null) {
            return null;
        }
        final String eventTitle = titleService.titleOf(getNotable()) + ": " + getAbbreviated();
        return new CalendarEvent(getDate().toDateTimeAtStartOfDay(), getCalendarName(), eventTitle);
    }
    //endregion

    //region > Functions

    public final static class Functions {
        private Functions() {}
        public final static Function<Note, CalendarEvent> TO_CALENDAR_EVENT = input -> input.toCalendarEvent();
        public final static Function<Note, String> GET_CALENDAR_NAME = input -> input.getCalendarName();
    }
    //endregion

    //region > toString, compareTo

    @Override
    public String toString() {
        return ObjectContracts.toString(this, "date", "calendarName");
    }

    @Override
    public int compareTo(final Note other) {
        return ObjectContracts.compare(this, other, "date", "calendarName");
    }

    //endregion

    //region > injected services
    @Inject
    NotableLinkRepository notableLinkRepository;
    @Inject
    RepositoryService repositoryService;
    @Inject
    TitleService titleService;
    @Inject
    LocaleProvider localeProvider;
    //endregion


}
