package org.incode.module.note.dom.impl.note;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;

import org.incode.module.note.NoteModule;
import org.incode.module.note.dom.impl.calendarname.CalendarNameService;
import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;

/**
 * An event that has or is scheduled to occur at some point in time, pertaining
 * to an {@link Notable}.
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
                        + "FROM org.incode.module.note.dom.impl.note.Note "
                        + "WHERE date >= :startDate "
                        + "   && date <= :endDate")
})
@DomainObject(editing = Editing.DISABLED)
public class Note implements CalendarEventable, Comparable<Note> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends NoteModule.PropertyDomainEvent<Note, T> {
        public PropertyDomainEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final Note source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends NoteModule.CollectionDomainEvent<Note, T> {
        public CollectionDomainEvent(final Note source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final Note source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends NoteModule.ActionDomainEvent<Note> {
        public ActionDomainEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final Note source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final Note source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > notable (property)

    public static class NotableDomainEvent extends PropertyDomainEvent<Notable> {
        public NotableDomainEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }
        public NotableDomainEvent(
                final Note source,
                final Identifier identifier,
                final Notable oldValue,
                final Notable newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    //region > title
    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(container.titleOf(getNotable()));
        if(getDate() != null) {
            buf.append(" @").append(container.titleOf(getDate()));
        }
        buf.append(": ").append(getNotesAbbreviated());
        return buf.toString();
    }

    //endregion

    /**
     * Polymorphic association to (any implementation of) {@link Notable}.
     */
    @Property(
            domainEvent = NotableDomainEvent.class,
            editing = Editing.DISABLED,
            hidden = Where.PARENTED_TABLES,
            notPersisted = true
    )
    public Notable getNotable() {
        final NotableLink link = getNotableLink();
        return link != null? link.getPolymorphicReference(): null;
    }

    @Programmatic
    public void setNotable(final Notable notable) {
        removeNotableLink();
        notableLinkRepository.createLink(this, notable);
    }

    private void removeNotableLink() {
        final NotableLink notableLink = getNotableLink();
        if(notableLink != null) {
            container.remove(notableLink);
        }
    }

    private NotableLink getNotableLink() {
        if (!container.isPersistent(this)) {
            return null;
        }
        return notableLinkRepository.findByNote(this);
    }
    //endregion

    //region > notesAbbreviated (property)

    public static class NotesAbbreviatedDomainEvent extends PropertyDomainEvent<String> {
        public NotesAbbreviatedDomainEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }
        public NotesAbbreviatedDomainEvent(final Note source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    /**
     * Derived from {@link #getNotes()}, solely for use in title and in tables.
     */
    @javax.jdo.annotations.NotPersistent
    @Property(
            domainEvent = NotesAbbreviatedDomainEvent.class,
            hidden = Where.OBJECT_FORMS
    )
    @PropertyLayout(
            named = "Notes"
    )
    public String getNotesAbbreviated() {
        return trim(getNotes(), "...", 40 );
    }

    static String trim(final String notes, final String ending, final int length) {
        if(notes == null || notes.length() <= length) {
            return notes;
        }
        return notes.substring(0, length-ending.length()) + ending ;
    }
    //endregion

    //region > notes (property)

    public static class NotesDomainEvent extends PropertyDomainEvent<String> {
        public NotesDomainEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }
        public NotesDomainEvent(final Note source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String notes;

    /**
     * Hidden in tables, instead the derived {@link #getNotesAbbreviated()} is shown.
     */
    @javax.jdo.annotations.Column(allowsNull = "true", length = NoteModule.JdoColumnLength.NOTES)
    @Property(
            domainEvent = NotesDomainEvent.class,
            hidden = Where.ALL_TABLES
    )
    @PropertyLayout(
            multiLine = NoteModule.MultiLine.NOTES
    )
    public String getNotes() {
        return notes;
    }

    public void setNotes(final String description) {
        this.notes = description;
    }
    //endregion

    //region > changeNotes (action)

    public static class ChangeNotesDomainEvent extends ActionDomainEvent {
        public ChangeNotesDomainEvent(final Note source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = ChangeNotesDomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Note changeNotes(
            @ParameterLayout(named = "Notes", multiLine = NoteModule.MultiLine.NOTES)
            final String notes) {
        setNotes(notes);

        return this;
    }

    public String default0ChangeNotes() {
        return getNotes();
    }

    public String validateChangeNotes(final String notes) {
        if(Strings.isNullOrEmpty(notes) && getDate() == null) {
            return "Must specify either note text or a date (or both).";
        }
        return null;
    }

    //endregion

    //region > date (property)

    public static class DateDomainEvent extends PropertyDomainEvent<LocalDate> {
        public DateDomainEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }
        public DateDomainEvent(final Note source, final Identifier identifier, final LocalDate oldValue, final LocalDate newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private LocalDate date;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(
            domainEvent = DateDomainEvent.class
    )
    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate startDate) {
        this.date = startDate;
    }

    //endregion

    //region > calendarName (property)

    public static class CalendarNameDomainEvent extends PropertyDomainEvent<String> {
        public CalendarNameDomainEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }
        public CalendarNameDomainEvent(final Note source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String calendarName;

    /**
     * The name of the &quot;calendar&quot; (if any) to which this note belongs.
     * 
     * <p>
     * The &quot;calendar&quot; is a string identifier that indicates the nature
     * of a note.  These are expected to be uniquely identifiable for all and
     * any notes that might be created. They therefore typically
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
    @javax.jdo.annotations.Column(allowsNull = "true", length = NoteModule.JdoColumnLength.CALENDAR_NAME)
    @Property(
            domainEvent = CalendarNameDomainEvent.class,
            editing = Editing.DISABLED
    )
    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(final String calendarName) {
        this.calendarName = calendarName;
    }

    public boolean hideCalendarName() {
        return getCalendarName() == null;
    }

    //endregion

    //region > changeDate (action)

    public static class ChangeDateDomainEvent extends ActionDomainEvent {
        public ChangeDateDomainEvent(final Note source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = ChangeDateDomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Note changeDate(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Date")
            final LocalDate date,
            @ParameterLayout(named = "Calendar")
            final String calendarName) {
        setDate(date);
        setCalendarName(calendarName);
        notableLinkRepository.updateLink(this);
        return this;
    }

    public Collection<String> choices1ChangeDate() {
        final Collection<String> values = calendarNameService.calendarNamesFor(getNotable());
        final List<String> valuesCopy = Lists.newArrayList(values);
        final List<String> currentCalendarsInUse = Lists.transform(
                noteRepository.findByNotable(getNotable()),
                Note::getCalendarName);
        valuesCopy.removeAll(currentCalendarsInUse);
        valuesCopy.add(getCalendarName()); // add back in current for this note's notable
        return valuesCopy;
    }

    public LocalDate default0ChangeDate() {
        return getDate();
    }

    public String default1ChangeDate() {
        return getCalendarName();
    }

    public String validateChangeDate(final LocalDate date, final String calendarName) {
        if(Strings.isNullOrEmpty(getNotes()) && (date == null || calendarName == null)) {
            return "Must specify either note text or a date/calendar (or both).";
        }
        return null;
    }

    //endregion

    //region > remove (action)
    public static class RemoveEvent extends ActionDomainEvent {
        public RemoveEvent(final Note source, final Identifier identifier) {
            super(source, identifier);
        }

        public RemoveEvent(final Note source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public RemoveEvent(
                final Note source,
                final Identifier identifier,
                final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = RemoveEvent.class,
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Notable remove(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Are you sure?")
            final Boolean areYouSure
    ) {
        final Notable notable = getNotable();
        noteRepository.remove(this);
        return notable;
    }

    public String validate0Remove(final Boolean areYouSure) {
        return areYouSure != null && areYouSure
                ? null
                : "Check the 'are you sure' to continue";
    }

    //endregion

    //region > CalendarEventable impl

    @Programmatic
    public CalendarEvent toCalendarEvent() {
        if(getDate() == null || getCalendarName() == null) {
            return null;
        }
        final String eventTitle = container.titleOf(getNotable()) + ": " + getNotesAbbreviated();
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
        return ObjectContracts.compare(this, other, "date", "source", "calendarName");
    }

    //endregion

    //region > injected

    @Inject
    private CalendarNameService calendarNameService;
    @Inject
    NoteRepository noteRepository;
    @Inject
    private NotableLinkRepository notableLinkRepository;
    @Inject
    private NoteContributionsOnNotable noteContributionsOnNotable;
    @Inject
    DomainObjectContainer container;
    //endregion

}
