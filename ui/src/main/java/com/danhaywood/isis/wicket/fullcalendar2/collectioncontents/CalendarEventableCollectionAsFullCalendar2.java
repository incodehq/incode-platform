/*
 *  Copyright 2013 Dan Haywood
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
package com.danhaywood.isis.wicket.fullcalendar2.collectioncontents;

import java.util.Collection;
import java.util.Set;

import com.danhaywood.isis.wicket.fullcalendar2.CalendaredCollectionAbstract;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import net.ftlines.wicket.fullcalendar.EventProvider;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;

public class CalendarEventableCollectionAsFullCalendar2 extends CalendaredCollectionAbstract {

    private static final long serialVersionUID = 1L;
    
    public CalendarEventableCollectionAsFullCalendar2(final String id, final EntityCollectionModel model) {
        super(id, model);
    }

    @Override
    protected EventProvider newEventProvider(EntityCollectionModel model, String calendarName) {
        return new CalendarEventableEventProvider(model, calendarName);
    }

    @Override
    protected Set<String> getCalendarNames(Collection<ObjectAdapter> entityList) {
        return Sets.newLinkedHashSet(
                Iterables.transform(entityList, CalendarEventableEventProvider.GET_CALENDAR_NAME));
    }

}
