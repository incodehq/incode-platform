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

import com.google.common.collect.ImmutableMap;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.session.SessionLoggingService;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
public class SessionLogEntryRepository {

    private static final String DN_BULK_UPDATES_KEY = "datanucleus.query.jdoql.allowAll".toLowerCase();

    //region > logoutAllSessions
    @Programmatic
    public void logoutAllSessions(final Timestamp logoutTimestamp) {

        final PersistenceManager pm = isisJdoSupport.getJdoPersistenceManager();

        final Properties properties = pm.getPersistenceManagerFactory().getProperties();
        if(isTrue(properties.get(DN_BULK_UPDATES_KEY))) {
            final javax.jdo.Query jdoQuery = pm.newNamedQuery(SessionLogEntry.class, "logoutAllActiveSessions");

            final Map argumentsByParameterName = ImmutableMap.of(
                    "logoutTimestamp", logoutTimestamp,
                    "causedBy2", SessionLogEntry.CausedBy2.RESTART);
            try {
                final Long numRows = (Long) jdoQuery.executeWithMap(argumentsByParameterName);
            } finally {
                jdoQuery.closeAll();
            }
        } else {
            final List<SessionLogEntry> activeEntries =
                    repositoryService.allMatches(new QueryDefault<>(SessionLogEntry.class, "listAllActiveSessions"));
            for (SessionLogEntry activeEntry : activeEntries) {
                activeEntry.setCausedBy2(SessionLogEntry.CausedBy2.RESTART);
                activeEntry.setLogoutTimestamp(logoutTimestamp);
            }
        }
    }

    private static boolean isTrue(Object o) {
        return o instanceof Boolean && (Boolean) o;
    }
    //endregion

    //region > create
    @Programmatic
    public void create(
            final String username,
            final String sessionId,
            final SessionLoggingService.CausedBy causedBy,
            final Timestamp timestamp) {
        SessionLogEntry sessionLogEntry;
        sessionLogEntry = repositoryService.instantiate(SessionLogEntry.class);
        sessionLogEntry.setUser(username);
        sessionLogEntry.setLoginTimestamp(timestamp);
        sessionLogEntry.setSessionId(sessionId);
        sessionLogEntry.setCausedBy(causedBy);
        repositoryService.persistAndFlush(sessionLogEntry);
    }
    //endregion


    //region > findBySessionId
    @Programmatic
    public SessionLogEntry findBySessionId(final String sessionId) {
        return repositoryService.firstMatch(
                new QueryDefault<>(SessionLogEntry.class,
                        "findBySessionId",
                        "sessionId", sessionId));
    }
    //endregion

    //region > findByUser

    @Programmatic
    public List<SessionLogEntry> findByUser(final String user) {
        return repositoryService.allMatches(
                new QueryDefault<>(SessionLogEntry.class,
                        "findByUser",
                        "user", user));
    }

    //endregion

    //region > findByUserAndFromAndTo


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
        return repositoryService.allMatches(query);
    }

    //endregion

    //region > findByFromAndTo


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
        return repositoryService.allMatches(query);
    }
    //endregion

    //region > findByUserAndStrictlyBefore


    @Programmatic
    public List<SessionLogEntry> findByUserAndStrictlyBefore(
            final String user,
            final Timestamp from) {

        return repositoryService.allMatches(new QueryDefault<>(SessionLogEntry.class,
                "findByUserAndTimestampStrictlyBefore",
                "user", user,
                "from", from));
    }
    //endregion

    //region > findByUserAndStrictlyAfter


    @Programmatic
    public List<SessionLogEntry> findByUserAndStrictlyAfter(
            final String user,
            final Timestamp from) {

        return repositoryService.allMatches(new QueryDefault<>(SessionLogEntry.class,
                "findByUserAndTimestampStrictlyAfter",
                "user", user,
                "from", from));
    }
    //endregion

    //region > listAllActiveSessions


    @Programmatic
    public List<SessionLogEntry> listAllActiveSessions() {

        return repositoryService.allMatches(new QueryDefault<>(SessionLogEntry.class,"listAllActiveSessions"));
    }
    //endregion

    //region > findRecentByUser


    @Programmatic
    public List<SessionLogEntry> findRecentByUser(final String user) {
        return repositoryService.allMatches(
                new QueryDefault<>(SessionLogEntry.class, "findRecentByUser", "user", user));

    }
    //endregion

    //region > helpers

    private static Timestamp toTimestampStartOfDayWithOffset(final LocalDate dt, final int daysOffset) {
        return dt!=null
                ?new Timestamp(dt.toDateTimeAtStartOfDay().plusDays(daysOffset).getMillis())
                :null;
    }

    //endregion

    //region > injected services
    @Inject
    RepositoryService repositoryService;
    @Inject
    IsisJdoSupport isisJdoSupport;
    //endregion

}
