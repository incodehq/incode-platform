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

import org.apache.isis.applib.adapters.DefaultsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.applib.adapters.ValueSemanticsProvider;

/**
 * For internal use; allows Isis to parse etc.
 */
public class CalendarEventSemanticsProvider implements ValueSemanticsProvider<CalendarEvent> {

	public DefaultsProvider<CalendarEvent> getDefaultsProvider() {
		return null;
	}

	public EncoderDecoder<CalendarEvent> getEncoderDecoder() {
	    return null;
	}

	public Parser<CalendarEvent> getParser() {
	    return null;
	}

	public boolean isEqualByContent() {
		return true;
	}

	public boolean isImmutable() {
		return true;
	}

}
