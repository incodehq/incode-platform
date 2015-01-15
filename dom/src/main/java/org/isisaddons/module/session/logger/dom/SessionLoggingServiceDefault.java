package org.isisaddons.module.session.logger.dom;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.session.SessionLoggingService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 */
@DomainService
@DomainServiceLayout(menuBar = DomainServiceLayout.MenuBar.SECONDARY, named = "Session history")
public class SessionLoggingServiceDefault extends AbstractService implements SessionLoggingService {

    @Programmatic
    @Override
    public void log(Type type, String username, Date date, CausedBy causedBy) {
        SessionLogEntry sessionLogEntry = newTransientInstance(SessionLogEntry.class);
        sessionLogEntry.setType(type);
        sessionLogEntry.setUsername(username);
        sessionLogEntry.setTimestamp(new Timestamp(new Date().getTime()));
        sessionLogEntry.setCausedBy(causedBy);
        persistIfNotAlready(sessionLogEntry);
    }

    public List<SessionLogEntry> allEntries() {
        return allInstances(SessionLogEntry.class);
    }
}
