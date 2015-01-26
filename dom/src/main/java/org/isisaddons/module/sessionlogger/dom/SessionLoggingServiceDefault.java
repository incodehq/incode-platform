package org.isisaddons.module.sessionlogger.dom;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.session.SessionLoggingService;

/**
 * Implementation of the Isis {@link org.apache.isis.applib.services.session.SessionLoggingService} creates a log
 * entry to the database (the {@link org.isisaddons.module.sessionlogger.dom.SessionLogEntry} entity) each time a
 * user either logs on or logs out, or if their session expires.
 */
@DomainService
public class SessionLoggingServiceDefault extends AbstractService implements SessionLoggingService {

    @Programmatic
    @Override
    public void log(final Type type, final String username, final Date date, final CausedBy causedBy, final String sessionId) {
        final Timestamp timestamp = new Timestamp(new Date().getTime());
        final SessionLogEntry sessionLogEntry;
        if (type == Type.LOGIN) {
            sessionLogEntry = newTransientInstance(SessionLogEntry.class);
            sessionLogEntry.setUsername(username);
            sessionLogEntry.setLoginTimestamp(timestamp);
            sessionLogEntry.setSessionId(sessionId);
        } else {
            sessionLogEntry = firstMatch(new QueryDefault<>(SessionLogEntry.class, "findBySessionId", "sessionId", sessionId));
            if (sessionLogEntry == null) {
                // invalidation of a never authenticated session
                return;
            }
            sessionLogEntry.setLogoutTimestamp(timestamp);
        }
        sessionLogEntry.setCausedBy(causedBy);
        persistIfNotAlready(sessionLogEntry);
    }

}
