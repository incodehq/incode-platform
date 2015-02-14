/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.sessionlogger.dom;

import java.sql.Timestamp;
import java.util.List;
import org.joda.time.LocalDate;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;

/**
 * Provides supporting functionality for querying
 * {@link org.isisaddons.module.sessionlogger.dom.SessionLogEntry session log entry} entities.
 *
 * <p>
 * This supporting service with no UI and no side-effects, and there are no other implementations of the service,
 * thus has been annotated with {@link org.apache.isis.applib.annotation.DomainService}.  This means that there is no
 * need to explicitly register it as a service (eg in <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class SessionLogEntryRepository extends AbstractFactoryAndRepository {

    @Programmatic
    public List<SessionLogEntry> findByUsername(final String username) {
        return allMatches(
                new QueryDefault<>(SessionLogEntry.class,
                        "findByUsername",
                        "username", username));
    }

    @Programmatic
    public List<SessionLogEntry> findByUsernameAndFromAndTo(
            final String username,
            final LocalDate from,
            final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);

        final Query<SessionLogEntry> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUsernameAndTimestampBetween",
                        "username", username,
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUsernameAndTimestampAfter",
                        "username", username,
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUsernameAndTimestampBefore",
                        "username", username,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUsername",
                        "username", username);
            }
        }
        return allMatches(query);
    }

    @Programmatic
    public List<SessionLogEntry> findByFromAndTo(
            final LocalDate from,
            final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);

        final Query<SessionLogEntry> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByTimestampBetween",
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByTimestampAfter",
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByTimestampBefore",
                        "to", toTs);
            } else {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "find");
            }
        }
        return allMatches(query);
    }

    @Programmatic
    public List<SessionLogEntry> findByUsernameAndStrictlyBefore(
            final String username,
            final Timestamp from) {

        return allMatches(new QueryDefault<>(SessionLogEntry.class,
                "findByUsernameAndTimestampStrictlyBefore",
                "username", username,
                "from", from));
    }

    @Programmatic
    public List<SessionLogEntry> findByUsernameAndStrictlyAfter(
            final String username,
            final Timestamp from) {

        return allMatches(new QueryDefault<>(SessionLogEntry.class,
                "findByUsernameAndTimestampStrictlyAfter",
                "username", username,
                "from", from));
    }


    @Programmatic
    public List<SessionLogEntry> listAllActiveSessions() {

        return allMatches(new QueryDefault<>(SessionLogEntry.class,"listAllActiveSessions"));
    }


    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, final int daysOffset) {
        return dt!=null
                ?new Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                :null;
    }
}
