/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.incode.module.note.dom.impl.notablelink;

import java.util.Set;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.Calendarable;

import org.incode.module.note.dom.NoteModule;
import org.incode.module.note.dom.impl.calendarname.CalendarNameService;
import org.incode.module.note.dom.impl.note.Note;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "incodeNote",
        table = "NotableLink"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByNote", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.note.dom.impl.notablelink.NotableLink "
                        + "WHERE note == :note"),
        @javax.jdo.annotations.Query(
                name = "findByNotable", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.note.dom.impl.notablelink.NotableLink "
                        + "WHERE notableObjectType == :notableObjectType "
                        + "   && notableIdentifier == :notableIdentifier "),
        @javax.jdo.annotations.Query(
                name = "findByNotableAndCalendarName", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.note.dom.impl.notablelink.NotableLink "
                        + "WHERE notableObjectType == :notableObjectType "
                        + "   && notableIdentifier == :notableIdentifier "
                        + "   && calendarName == :calendarName"),
        @javax.jdo.annotations.Query(
                name = "findByNotableInDateRange", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.note.dom.impl.notablelink.NotableLink "
                        + "WHERE notableObjectType == :notableObjectType "
                        + "   && notableIdentifier == :notableIdentifier "
                        + "   && date >= :startDate "
                        + "   && date <= :endDate")
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "NotableLink_notable_IDX",
                members = { "notableObjectType", "notableIdentifier" })
})
@javax.jdo.annotations.Uniques({
        @javax.jdo.annotations.Unique(
                name="NotableLink_note_UNQ",
                members = {"note"})
})
@DomainObject(
        objectType = "note.NotableLink"
)
public abstract class NotableLink
        extends PolymorphicAssociationLink<Note, Object, NotableLink>
        implements Calendarable {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends NoteModule.PropertyDomainEvent<NotableLink, T> { }
    public static abstract class CollectionDomainEvent<T> extends NoteModule.CollectionDomainEvent<NotableLink, T> { }
    public static abstract class ActionDomainEvent extends NoteModule.ActionDomainEvent<NotableLink> { }
    //endregion

    //region > instantiateEvent (poly pattern)
    public static class InstantiateEvent
            extends PolymorphicAssociationLink.InstantiateEvent<Note, Object, NotableLink> {

        public InstantiateEvent(final Object source, final Note subject, final Object notable) {
            super(NotableLink.class, source, subject, notable);
        }
    }
    //endregion

    //region > constructor
    public NotableLink() {
        super("{polymorphicReference} has {subject}");
    }
    //endregion

    //region > SubjectPolymorphicReferenceLink API

    /**
     * The subject of the pattern, which (perhaps confusingly in this instance) is actually the
     * {@link #getNote() event}.
     */
    @Override
    @Programmatic
    public Note getSubject() {
        return getNote();
    }

    @Override
    @Programmatic
    public void setSubject(final Note subject) {
        setNote(subject);
    }

    @Override
    @Programmatic
    public String getPolymorphicObjectType() {
        return getNotableObjectType();
    }

    @Override
    @Programmatic
    public void setPolymorphicObjectType(final String polymorphicObjectType) {
        setNotableObjectType(polymorphicObjectType);
    }

    @Override
    @Programmatic
    public String getPolymorphicIdentifier() {
        return getNotableIdentifier();
    }

    @Override
    @Programmatic
    public void setPolymorphicIdentifier(final String polymorphicIdentifier) {
        setNotableIdentifier(polymorphicIdentifier);
    }
    //endregion


    public static class NoteDomainEvent extends PropertyDomainEvent<Note> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", name = "noteId")
    @Property(
            domainEvent = NoteDomainEvent.class,
            editing = Editing.DISABLED
    )
    private Note note;

    public static class NotableObjectTypeDomainEvent extends PropertyDomainEvent<String> { }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = NotableObjectTypeDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String notableObjectType;

    public static class NotableIdentifierDomainEvent extends PropertyDomainEvent<String> {
    }
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = NotableIdentifierDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String notableIdentifier;

    public static class DateDomainEvent extends PropertyDomainEvent<LocalDate> { }
    /**
     * Copy of the {@link #getNote() note}'s {@link Note#getDate() date}, to support querying.
     *
     * <p>
     *     If the {@link Note#getDate()} is changed, then this derived property is also updated.
     * </p>
     */
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(
            domainEvent = DateDomainEvent.class
    )
    private LocalDate date;

    public static class CalendarNameDomainEvent extends PropertyDomainEvent<String> { }
    /**
     * Copy of the {@link #getNote() note}'s {@link Note#getCalendarName() calendar name}, to support querying.
     *
     * <p>
     *     If the {@link Note#getCalendarName()} is changed, then this derived property is also updated.
     * </p>
     */
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull = "true", length= NoteModule.JdoColumnLength.CALENDAR_NAME)
    @Property(
            domainEvent = CalendarNameDomainEvent.class,
            editing = Editing.DISABLED
    )
    private String calendarName;


    //region > notable (derived property)
    /**
     * Simply returns the {@link #getPolymorphicReference()}.
     */
    @Programmatic
    public Object getNotable() {
        return getPolymorphicReference();
    }
    //endregion

    //region > eventSource impl

    /**
     * Can add to all calendars
     */
    @Programmatic
    @Override
    public Set<String> getCalendarNames() {
        return Sets.newTreeSet(calendarNameService.calendarNamesFor(getNotable()));
    }

    /**
     * to display in fullcalendar2
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Programmatic
    @Override
    public ImmutableMap<String, CalendarEventable> getCalendarEvents() {
        return ImmutableMap.<String, CalendarEventable>of(getCalendarName(), getNote());
    }

    //endregion

    //region > Functions
    public static class Functions {
        public static Function<NotableLink, Note> note() {
            return note(Note.class);
        }
        public static <T extends Note> Function<NotableLink, T> note(Class<T> cls) {
            return input -> input != null
                                ? (T)input.getNote()
                                : null;
        }
        public static Function<NotableLink, Object> notable() {
            return notable(Object.class);
        }

        public static <T extends Object> Function<NotableLink, T> notable(final Class<T> cls) {
            return input -> input != null
                                ? (T)input.getNotable()
                                : null;
        }
    }
    //endregion

    //region  >  (injected)
    @Inject
    CalendarNameService calendarNameService;
    //endregion
    
}
