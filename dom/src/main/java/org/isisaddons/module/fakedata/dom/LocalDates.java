/*
 *  Copyright 2015 Dan Haywood
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
package org.isisaddons.module.fakedata.dom;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.apache.isis.applib.annotation.Programmatic;

public class LocalDates extends AbstractRandomValueGenerator{

    public LocalDates(final FakeDataService fakeDataService) {
        super(fakeDataService);
    }

    @Programmatic
    public LocalDate around(final Period period) {
        final LocalDate now = fakeDataService.clockService.now();
        return fakeDataService.booleans().coinFlip() ? before(period) : after(period);
    }

    @Programmatic
    public LocalDate before(final Period period) {
        final LocalDate now = fakeDataService.clockService.now();
        return now.minus(period);
    }

    @Programmatic
    public LocalDate after(final Period period) {
        final LocalDate now = fakeDataService.clockService.now();
        return now.plus(period);
    }

}
