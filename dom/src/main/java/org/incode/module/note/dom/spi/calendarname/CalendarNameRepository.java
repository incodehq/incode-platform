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
package org.incode.module.note.dom.spi.calendarname;

import java.util.Collection;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.note.Note;

/**
 * Optional SPI service
 */
public interface CalendarNameRepository {

    /**
     * Return a collection of objects to act as calendars for the {@link Note}s to attach to the specified {@link Notable}.
     *
     * <p>
     *     May return null if there are none (in which case a default name will be used).
     * </p>
     */
    Collection<String> calendarNamesFor(Object notable);

}
