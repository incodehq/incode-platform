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

import java.util.Map;

import com.danhaywood.isis.wicket.fullcalendar2.EventProviderAbstract;
import com.danhaywood.isis.wicket.fullcalendar2.applib.CalendarEvent;
import com.danhaywood.isis.wicket.fullcalendar2.applib.CalendarEventable;
import com.danhaywood.isis.wicket.fullcalendar2.applib.Calendarable;
import com.google.common.base.Function;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;

public class CalendarableEventProvider extends EventProviderAbstract {

    private static final long serialVersionUID = 1L;

    // //////////////////////////////////////

    public CalendarableEventProvider(final EntityCollectionModel collectionModel, final String calendarName) {
        super(collectionModel, calendarName);
    }

    protected CalendarEvent calendarEventFor(final Object domainObject, final String calendarName) {
        final Calendarable calendarable = (Calendarable)domainObject;
        final Map<String, CalendarEventable> calendarEvents = calendarable.getCalendarEvents();
        final CalendarEventable calendarEventable = calendarEvents.get(calendarName);
        return calendarEventable!=null
                ?calendarEventable.toCalendarEvent()
                :null;
    }

    // //////////////////////////////////////

    static final Function<ObjectAdapter, Iterable<String>> GET_CALENDAR_NAMES = new Function<ObjectAdapter, Iterable<String>>() {
        @Override
        public Iterable<String> apply(final ObjectAdapter input) {
            final Calendarable calendarable = (Calendarable)input.getObject();
            return calendarable.getCalendarNames();
        }
    };


}
