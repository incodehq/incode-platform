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

import com.danhaywood.isis.wicket.fullcalendar2.CalendaredCollectionFactoryAbstract;
import com.danhaywood.isis.wicket.fullcalendar2.applib.CalendarEventable;

import org.apache.wicket.Component;

import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;

/**
 * {@link ComponentFactory} for {@link CalendarEventableCollectionAsFullCalendar2}.
 */
public class CalendarEventableCollectionAsFullCalendar2Factory extends CalendaredCollectionFactoryAbstract {

    private static final long serialVersionUID = 1L;

    public CalendarEventableCollectionAsFullCalendar2Factory() {
        super(CalendarEventable.class);
    }

    @Override
    protected Component newComponent(String id, EntityCollectionModel collectionModel) {
        return new CalendarEventableCollectionAsFullCalendar2(id, collectionModel);
    }
}
