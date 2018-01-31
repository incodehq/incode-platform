package org.isisaddons.module.command.replay.impl;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixtures.TickingFixtureClock;
import org.apache.isis.core.metamodel.services.configinternal.ConfigurationServiceInternal;

/**
 * If configured as the slave, then sets up to use {@link TickingFixtureClock}
 * so that time can be changed dynamically when running.
 */
@DomainService(nature = NatureOfService.DOMAIN, menuOrder = "1")
public class TickingClockService {

    @PostConstruct
    public void init() {
        if( notDefined(Constants.MASTER_BASE_URL_ISIS_KEY) ||
            notDefined(Constants.MASTER_USER_ISIS_KEY) ||
            notDefined(Constants.MASTER_PASSWORD_ISIS_KEY)) {
            return;
        }
        TickingFixtureClock.replaceExisting();
    }

    private boolean notDefined(final String key) {
        return configurationServiceInternal.getProperty(key) == null;
    }

    @Programmatic
    public void at(Timestamp timestamp, Runnable runnable) {
        final TickingFixtureClock instance = (TickingFixtureClock) TickingFixtureClock.getInstance();
        final Timestamp previous = TickingFixtureClock.getTimeAsJavaSqlTimestamp();
        try {
            instance.setTime(timestamp);
            runnable.run();
        } finally {
            instance.setTime(previous);
        }
    }

    @Programmatic
    public <T> T at(Timestamp timestamp, Callable<T> callable) {
        final TickingFixtureClock instance = (TickingFixtureClock) TickingFixtureClock.getInstance();
        final Timestamp previous = TickingFixtureClock.getTimeAsJavaSqlTimestamp();
        try {
            instance.setTime(timestamp);
            return callable.call();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            instance.setTime(previous);
        }
    }

    @Inject
    ConfigurationServiceInternal configurationServiceInternal;
}