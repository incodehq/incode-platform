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
package com.danhaywood.isis.wicket.fullcalendar2.applib;

import java.io.Serializable;

import org.joda.time.DateTime;

import org.apache.isis.applib.annotation.Value;
import org.apache.isis.applib.util.ObjectContracts;

/**
 * Value type representing an event on a calendar.
 */
@Value(semanticsProviderClass=CalendarEventSemanticsProvider.class)
public class CalendarEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final String KEY_FIELDS = "dateTime, calendarName";
	static final CalendarEvent DEFAULT_VALUE = null; // no default
	
    private final DateTime dateTime;
    private final String calendarName;
    private final String title;
    private final String notes;
	
	public CalendarEvent(final DateTime dateTime, final String calendarName, final String title) {
        this(dateTime, calendarName, title, null);
	}

    public CalendarEvent(final DateTime dateTime, final String calendarName, final String title, final String notes) {
        this.dateTime = dateTime;
        this.calendarName = calendarName;
        this.title = title;
        this.notes = notes;
    }

    public DateTime getDateTime() {
        return dateTime;
    }
    
    public String getCalendarName() {
        return calendarName;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getNotes() {
        return notes;
    }
   
	public CalendarEvent withDateTime(final DateTime date) {
		return new CalendarEvent(date, this.calendarName, this.title, this.notes);
	}
	
	public CalendarEvent withCalendarName(final String calendarName) {
	    return new CalendarEvent(this.dateTime, calendarName, this.title, this.notes);
	}
	
	public CalendarEvent withTitle(final String title) {
	    return new CalendarEvent(this.dateTime, this.calendarName, title, this.notes);
	}
	
	public CalendarEvent withNotes(final String notes) {
	    return new CalendarEvent(this.dateTime, this.calendarName, this.title, notes);
	}

	@Override
    public int hashCode() {
	    return ObjectContracts.hashCode(this, KEY_FIELDS);
    }

    @Override
    public boolean equals(Object obj) {
        return ObjectContracts.equals(this, obj, KEY_FIELDS);
    }

    @Override
    public String toString() {
        return ObjectContracts.toString(this, KEY_FIELDS);
    }
    
    public static int typicalLength() {
		return 30;
	}
}
