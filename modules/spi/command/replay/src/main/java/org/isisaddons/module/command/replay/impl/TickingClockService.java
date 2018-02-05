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
 * If configured as the slave, then sets up to use {@link TickingFixtureClock} so that time can be changed dynamically
 * when running.
 *
 * <p>
 *     If the configuration keys for a replay slave are not provided, then the service will not initialize.
 * </p>
 *
 * <p>
 *     IMPORTANT: the methods provided by this service are not thread-safe, because the clock is a globally-scoped
 *     singleton rather than a thread-local.  This method should therefore only be used in single-user systems,
 *     eg a replay slave.
 * </p>
 */
@DomainService(nature = NatureOfService.DOMAIN, menuOrder = "1")
public class TickingClockService {

    private final static Logger LOG = LoggerFactory.getLogger(TickingClockService.class);

    @PostConstruct
    public void init() {
        if( notDefined(ConfigurationKeys.MASTER_BASE_URL_ISIS_KEY) ||
            notDefined(ConfigurationKeys.MASTER_USER_ISIS_KEY) ||
            notDefined(ConfigurationKeys.MASTER_PASSWORD_ISIS_KEY)) {
            LOG.info(
                    "init() - skipping, one or more {}.* configuration constants missing",
                    ConfigurationKeys.ISIS_KEY_PREFIX);
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

    /**
     * Executes the runnable, setting the clock to be the specified time beforehand (and reinstating it to its original
     * time afterwards).
     *
     * <p>
     *     IMPORTANT: this method is not thread-safe, because the clock is a globally-scoped singleton rather than a
     *     thread-local.  This method should therefore only be used in single-user systems, eg a replay slave.
     * </p>
     */
    @Programmatic
    public void at(Timestamp timestamp, Runnable runnable) {
        ensureInitialized();

        final TickingFixtureClock instance = (TickingFixtureClock) TickingFixtureClock.getInstance();
        final long previous = TickingFixtureClock.getTimeAsMillis();
        final long wallTime0 = System.currentTimeMillis();
        try {
            instance.setTime(timestamp);
            runnable.run();
        } finally {
            final long wallTime1 = System.currentTimeMillis();
            instance.setTime(previous + wallTime1 - wallTime0);
        }
    }

    /**
     * Executes the callable, setting the clock to be the specified time beforehand (and reinstating it to its original
     * time afterwards).
     *
     * <p>
     *     IMPORTANT: this method is not thread-safe, because the clock is a globally-scoped singleton rather than a
     *     thread-local.  This method should therefore only be used in single-user systems, eg a replay slave.
     * </p>
     */
    @Programmatic
    public <T> T at(Timestamp timestamp, Callable<T> callable) {
        ensureInitialized();

        final TickingFixtureClock instance = (TickingFixtureClock) TickingFixtureClock.getInstance();
        final long previous = TickingFixtureClock.getTimeAsMillis();
        final long wallTime0 = System.currentTimeMillis();
        try {
            instance.setTime(timestamp);
            return callable.call();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            final long wallTime1 = System.currentTimeMillis();
            instance.setTime(previous + wallTime1 - wallTime0);
        }
    }

    private void ensureInitialized() {
        if(!isInitialized()) {
            throw new IllegalStateException(
                    "Not initialized.  Make sure that the application is configured to run as a replay slave");
        }
    }

    @Inject
    ConfigurationServiceInternal configurationServiceInternal;
}