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
package org.incode.module.note.dom;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.base.Function;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

import org.incode.module.note.NoteModule;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "isisevent",
        table = "EventSourceLink"
)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByEvent", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.note.dom.NoteSourceLink "
                        + "WHERE note == :note"),
        @javax.jdo.annotations.Query(
                name = "findBySource", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.note.dom.NoteSourceLink "
                        + "WHERE sourceObjectType == :sourceObjectType "
                        + "   && sourceIdentifier == :sourceIdentifier "),
        @javax.jdo.annotations.Query(
                name = "findBySourceAndCalendarName", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.module.note.dom.NoteSourceLink "
                        + "WHERE sourceObjectType == :sourceObjectType "
                        + "   && sourceIdentifier == :sourceIdentifier "
                        + "   && calendarName == :calendarName")
})
@javax.jdo.annotations.Indices({
        @javax.jdo.annotations.Index(
                name = "EventSourceLink_main_idx",
                members = { "sourceObjectType", "sourceIdentifier", "event" })
})
@javax.jdo.annotations.Unique(name="EventSourceLink_event_source_UNQ", members = {"event","sourceObjectType","sourceIdentifier"})
@DomainObject(
        objectType = "event.EventSourceLink"
)
public abstract class NotableLink extends PolymorphicAssociationLink<Note, Notable, NotableLink> {

    //region > event classes
    public static abstract class PropertyDomainEvent<T> extends NoteModule.PropertyDomainEvent<NotableLink, T> {
        public PropertyDomainEvent(final NotableLink source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final NotableLink source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends NoteModule.CollectionDomainEvent<NotableLink, T> {
        public CollectionDomainEvent(final NotableLink source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final NotableLink source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends NoteModule.ActionDomainEvent<NotableLink> {
        public ActionDomainEvent(final NotableLink source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final NotableLink source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final NotableLink source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }
    //endregion

    //region > instantiateEvent (poly pattern)
    public static class InstantiateEvent
            extends PolymorphicAssociationLink.InstantiateEvent<Note, Notable, NotableLink> {

        public InstantiateEvent(final Object source, final Note subject, final Notable owner) {
            super(NotableLink.class, source, subject, owner);
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
        return getSourceObjectType();
    }

    @Override
    @Programmatic
    public void setPolymorphicObjectType(final String polymorphicObjectType) {
        setSourceObjectType(polymorphicObjectType);
    }

    @Override
    @Programmatic
    public String getPolymorphicIdentifier() {
        return getSourceIdentifier();
    }

    @Override
    @Programmatic
    public void setPolymorphicIdentifier(final String polymorphicIdentifier) {
        setSourceIdentifier(polymorphicIdentifier);
    }
    //endregion

    //region > event (property)

    public static class EventDomainEvent extends PropertyDomainEvent<Note> {
        public EventDomainEvent(final NotableLink source, final Identifier identifier) {
            super(source, identifier);
        }
        public EventDomainEvent(final NotableLink source, final Identifier identifier, final Note oldValue, final Note newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private Note note;
    @javax.jdo.annotations.Column(allowsNull = "false", name = "eventId")
    @Property(
            domainEvent = EventDomainEvent.class,
            editing = Editing.DISABLED
    )
    public Note getNote() {
        return note;
    }

    public void setNote(final Note note) {
        this.note = note;
    }
    //endregion

    //region > sourceObjectType (property)

    public static class SourceObjectTypeDomainEvent extends PropertyDomainEvent<String> {
        public SourceObjectTypeDomainEvent(final NotableLink source, final Identifier identifier) {
            super(source, identifier);
        }
        public SourceObjectTypeDomainEvent(final NotableLink source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String sourceObjectType;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = SourceObjectTypeDomainEvent.class,
            editing = Editing.DISABLED
    )
    public String getSourceObjectType() {
        return sourceObjectType;
    }

    public void setSourceObjectType(final String sourceObjectType) {
        this.sourceObjectType = sourceObjectType;
    }
    //endregion

    //region > sourceIdentifier (property)

    public static class SourceIdentifierDomainEvent extends PropertyDomainEvent<String> {
        public SourceIdentifierDomainEvent(final NotableLink source, final Identifier identifier) {
            super(source, identifier);
        }
        public SourceIdentifierDomainEvent(final NotableLink source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String sourceIdentifier;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(
            domainEvent = SourceIdentifierDomainEvent.class,
            editing = Editing.DISABLED
    )
    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(final String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }
    //endregion

    //region > calendarName (property)

    public static class CalendarNameDomainEvent extends PropertyDomainEvent<String> {
        public CalendarNameDomainEvent(final NotableLink source, final Identifier identifier) {
            super(source, identifier);
        }
        public CalendarNameDomainEvent(final NotableLink source, final Identifier identifier, final String oldValue, final String newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    private String calendarName;

    /**
     * Copy of the {@link #getNote() event}'s {@link Note#getCalendarName() calendar name}.
     *
     * <p>
     *     To support querying.  This is an immutable property of {@link Note} so it is safe to copy.
     * </p>
     */
    @javax.jdo.annotations.Column(allowsNull = "false", length= NoteModule.JdoColumnLength.CALENDAR_NAME)
    @Property(
            domainEvent = CalendarNameDomainEvent.class,
            editing = Editing.DISABLED
    )
    @Title(prepend=": ", sequence="2")
    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(final String calendarName) {
        this.calendarName = calendarName;
    }
    //endregion

    //region > Functions
    public static class Functions {
        public static Function<NotableLink, Note> event() {
            return event(Note.class);
        }
        public static <T extends Note> Function<NotableLink, T> event(Class<T> cls) {
            return new Function<NotableLink, T>() {
                @Override
                public T apply(final NotableLink input) {
                    return (T)input.getNote();
                }
            };
        }
        public static Function<NotableLink, Notable> owner() {
            return source(Notable.class);
        }

        public static <T extends Notable> Function<NotableLink, T> source(final Class<T> cls) {
            return new Function<NotableLink, T>() {
                @Override
                public T apply(final NotableLink input) {
                    return (T)input.getPolymorphicReference();
                }
            };
        }
    }
    //endregion

}
