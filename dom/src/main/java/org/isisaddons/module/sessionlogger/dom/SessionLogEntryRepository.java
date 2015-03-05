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
    public List<SessionLogEntry> findByUser(final String user) {
        return allMatches(
                new QueryDefault<>(SessionLogEntry.class,
                        "findByUser",
                        "user", user));
    }

    @Programmatic
    public List<SessionLogEntry> findByUserAndFromAndTo(
            final String user,
            final LocalDate from,
            final LocalDate to) {
        final Timestamp fromTs = toTimestampStartOfDayWithOffset(from, 0);
        final Timestamp toTs = toTimestampStartOfDayWithOffset(to, 1);

        final Query<SessionLogEntry> query;
        if(from != null) {
            if(to != null) {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUserAndTimestampBetween",
                        "user", user,
                        "from", fromTs,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUserAndTimestampAfter",
                        "user", user,
                        "from", fromTs);
            }
        } else {
            if(to != null) {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUserAndTimestampBefore",
                        "user", user,
                        "to", toTs);
            } else {
                query = new QueryDefault<>(SessionLogEntry.class,
                        "findByUser",
                        "user", user);
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
    public List<SessionLogEntry> findByUserAndStrictlyBefore(
            final String user,
            final Timestamp from) {

        return allMatches(new QueryDefault<>(SessionLogEntry.class,
                "findByUserAndTimestampStrictlyBefore",
                "user", user,
                "from", from));
    }

    @Programmatic
    public List<SessionLogEntry> findByUserAndStrictlyAfter(
            final String user,
            final Timestamp from) {

        return allMatches(new QueryDefault<>(SessionLogEntry.class,
                "findByUserAndTimestampStrictlyAfter",
                "user", user,
                "from", from));
    }


    @Programmatic
    public List<SessionLogEntry> listAllActiveSessions() {

        return allMatches(new QueryDefault<>(SessionLogEntry.class,"listAllActiveSessions"));
    }


    // //////////////////////////////////////

    @Programmatic
    public List<SessionLogEntry> findRecentByUser(final String user) {
        return allMatches(
                new QueryDefault<>(SessionLogEntry.class, "findRecentByUser", "user", user));

    }


    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, final int daysOffset) {
        return dt!=null
                ?new Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                :null;
    }



}
