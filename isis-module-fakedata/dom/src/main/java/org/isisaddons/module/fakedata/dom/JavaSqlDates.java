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

import java.sql.Date;
import org.joda.time.DateTime;
import org.apache.isis.applib.annotation.Programmatic;

public class JavaSqlDates extends AbstractRandomValueGenerator{

    public JavaSqlDates(final FakeDataService fakeDataService) {
        super(fakeDataService);
    }

    @Programmatic
    public java.sql.Date any() {
        final DateTime dateTime = fake.jodaDateTimes().any();
        final Date sqldt = asSqlDate(dateTime);
        return sqldt;
    }

    private static Date asSqlDate(final DateTime dateTime) {
        final DateTime dateTimeAtStartOfDay = dateTime.withTimeAtStartOfDay();
        return new Date(dateTimeAtStartOfDay.toDate().getTime());
    }
}
