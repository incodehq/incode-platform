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

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.api.notable.Notable;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = NotableLink.class
)
public class NotableLinkRepository {

    //region > init
    PolymorphicAssociationLink.Factory<Note,Notable,NotableLink,NotableLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        Note.class,
                        Notable.class,
                        NotableLink.class,
                        NotableLink.InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByNotable (programmatic)
    @Programmatic
    public NotableLink findByNote(final Note note) {
        return container.firstMatch(
                new QueryDefault<>(NotableLink.class,
                        "findByNote",
                        "note", note));
    }
    //endregion

    //region > findBySource (programmatic)
    @Programmatic
    public List<NotableLink> findBySource(final Notable notable) {
        if(notable == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(notable);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(NotableLink.class,
                        "findByNotable",
                        "notableObjectType", bookmark.getObjectType(),
                        "notableIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > findByNotableAndCalendarName (programmatic)
    @Programmatic
    public NotableLink findByNotableAndCalendarName(
            final Notable notable,
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
        return container.firstMatch(
                new QueryDefault<>(NotableLink.class,
                        "findByNotableAndCalendarName",
                        "notableObjectType", bookmark.getObjectType(),
                        "notableIdentifier", bookmark.getIdentifier(),
                        "calendarName", calendarName));
    }
    //endregion

    //region > createLink (programmatic)
    @Programmatic
    public NotableLink createLink(final Note note, final Notable notable) {
        final NotableLink link = linkFactory.createLink(note, notable);

        sync(note, link);

        return link;
    }

    //endregion

    @Programmatic
    public void updateLink(final Note note) {
        final NotableLink link = findByNote(note);
        sync(note, link);
    }

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


    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    BookmarkService bookmarkService;

    //endregion

}
