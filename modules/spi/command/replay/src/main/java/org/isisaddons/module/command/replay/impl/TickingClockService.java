package org.isisaddons.module.command.replay.impl;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.fixtures.TickingFixtureClock;
import org.apache.isis.core.metamodel.services.configinternal.ConfigurationServiceInternal;

/**
 * If configured as the slave, then sets up to use {@link TickingFixtureClock}
 * so that time can be changed dynamically when running.
 */
@DomainService(nature = NatureOfService.DOMAIN, menuOrder = "1")
public class TickingClockService {

    private final static Logger LOG = LoggerFactory.getLogger(TickingClockService.class);

    @PostConstruct
    public void init() {
        if( notDefined(Constants.MASTER_BASE_URL_ISIS_KEY) ||
            notDefined(Constants.MASTER_USER_ISIS_KEY) ||
            notDefined(Constants.MASTER_PASSWORD_ISIS_KEY)) {
            LOG.info(
                    "init() - skipping, one or more {}.* configuration constants missing",
                    Constants.ISIS_KEY_PREFIX);
            return;
        }

        LOG.info("init() - replacing existing clock with TickingFixtureClock");
        TickingFixtureClock.replaceExisting();
    }

    @Programmatic
    public boolean isInitialized() {
        return Clock.getInstance() instanceof TickingFixtureClock;
    }

    private boolean notDefined(final String key) {
        return getConfigProperties().get(key) == null;
    }

    /**
     * lazily loaded
     */
    private Map<String, String> configProperties;
    private Map<String, String> getConfigProperties() {
        if(configProperties == null) {
            configProperties = configurationServiceInternal.asMap();
        }
        return configProperties;
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