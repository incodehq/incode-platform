/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
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

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Note.class
)
public class NoteRepository {

    //region > findBySource (programmatic)
    @Programmatic
    public List<Note> findBySource(final Notable notable) {
        final List<NotableLink> links = notableLinkRepository.findBySource(notable);
        return Lists.newArrayList(
                Iterables.transform(links, NotableLink.Functions.event()));
    }
    //endregion

    //region > findBySourceAndCalendarName (programmatic)
    @Programmatic
    public Note findBySourceAndCalendarName(
            final Notable notable,
            final String calendarName) {
        final NotableLink link = notableLinkRepository.findBySourceAndCalendarName(notable, calendarName);
        return link != null? link.getNote(): null;
    }
    //endregion

    //region > newEvent (programmatic)
    @Programmatic
    public Note newEvent(
            final LocalDate date, final Notable notable, final String calendarName) {
        final Note note = container.newTransientInstance(Note.class);
        note.setDate(date);
        note.setCalendarName(calendarName);
        note.setSource(notable);
        container.persistIfNotAlready(note);

        return note;
    }
    //endregion

    //region > remove (programmatic)
    @Programmatic
    public void remove(Note note) {
        final NotableLink link = notableLinkRepository.findByEvent(note);
        container.removeIfNotAlready(link);
        container.flush();
        container.removeIfNotAlready(note);
        container.flush();
    }
    //endregion

    //region > findEventsInDateRange (programmatic)
    @Programmatic
    public List<Note> findEventsInDateRange(final LocalDate rangeStartDate, final LocalDate rangeEndDate) {
        return container.allMatches(
                new QueryDefault<>(
                        Note.class,
                        "findInDateRange",
                        "rangeStartDate", rangeStartDate,
                        "rangeEndDate", rangeEndDate));
    }
    //endregion

    //region > allEvents (programmatic)

    @Programmatic
    public List<Note> allEvents() {
        return container.allInstances(Note.class);
    }
    //endregion

    //region > injected
    @Inject
    NotableLinkRepository notableLinkRepository;
    @Inject
    DomainObjectContainer container;
    //endregion

}
