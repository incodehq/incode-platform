package org.isisaddons.module.sessionlogger.dom;

import java.sql.Timestamp;
import java.util.Date;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
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
    public void log(final Type type, final String username, final Date date, final CausedBy causedBy) {
        final SessionLogEntry sessionLogEntry = newTransientInstance(SessionLogEntry.class);
        sessionLogEntry.setType(type);
        sessionLogEntry.setUsername(username);
        sessionLogEntry.setTimestamp(new Timestamp(new Date().getTime()));
        sessionLogEntry.setCausedBy(causedBy);
        persistIfNotAlready(sessionLogEntry);
    }

}
