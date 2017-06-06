package org.isisaddons.module.sessionlogger.dom;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.session.SessionLoggingService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Implementation of the Isis {@link org.apache.isis.applib.services.session.SessionLoggingService} creates a log
 * entry to the database (the {@link org.isisaddons.module.sessionlogger.dom.SessionLogEntry} entity) each time a
 * user either logs on or logs out, or if their session expires.
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class SessionLoggingServiceDefault implements SessionLoggingService {

    @PostConstruct
    public void init() {
        final Timestamp timestamp = clockService.nowAsJavaSqlTimestamp();
        sessionLogEntryRepository.logoutAllSessions(timestamp);
    }

    @Programmatic
    @Override
    public void log(final Type type, final String username, final Date date, final CausedBy causedBy, final String sessionId) {
        final Timestamp timestamp = clockService.nowAsJavaSqlTimestamp();
        final SessionLogEntry sessionLogEntry;
        if (type == Type.LOGIN) {
            sessionLogEntryRepository.create(username, sessionId, causedBy, timestamp);
        } else {
            sessionLogEntry = sessionLogEntryRepository.findBySessionId(sessionId);
            if (sessionLogEntry == null) {
                // invalidation of a never authenticated session
                return;
            }
            sessionLogEntry.setLogoutTimestamp(timestamp);
            sessionLogEntry.setCausedBy(causedBy);
        }
    }

    @Inject
    SessionLogEntryRepository sessionLogEntryRepository;
    @Inject
    ClockService clockService;
}
