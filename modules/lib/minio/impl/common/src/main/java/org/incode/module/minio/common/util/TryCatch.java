package org.incode.module.minio.common.util;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to allow a try/catch to be attempted multiple times, with a recovery and automatic back-off.
 */
public class TryCatch {

    private static final Logger LOG = LoggerFactory.getLogger(TryCatch.class);

    public static final int MAX_ATTEMPTS = 5;

    public interface Backoff {
        void backoff(int attempt);

        class Default implements Backoff {

            @Override
            public void backoff(final int attempt) {
                sleep(attempt * 1000L);
            }

            /**
             * protected for convenience of any subclass.
             */
            protected static void sleep(final long sleepForMillis) {
                final long t1 = System.currentTimeMillis() + sleepForMillis;
                long remaining = t1 - System.currentTimeMillis();
                while(remaining > 0L) {
                    try {
                        Thread.sleep(remaining);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                    remaining = t1 - System.currentTimeMillis();
                }
            }

            @Override
            public String toString() {
                return "default backoff (1s, 2s, 3s, 4s, 5s)";
            }
        }
    }

    private final Backoff backoff;
    private final int maxAttempts;

    public TryCatch() {
        this(MAX_ATTEMPTS, new Backoff.Default());
    }

    public TryCatch(final Backoff backoff) {
        this(MAX_ATTEMPTS, backoff);
    }

    public TryCatch(final int maxAttempts) {
        this(maxAttempts, new Backoff.Default());
    }

    public TryCatch(final int maxAttempts, final Backoff backoff) {
        this.backoff = backoff;
        this.maxAttempts = maxAttempts;
    }

    public <T> T tryCatch(
            final Callable<T> tryCallable,
            final Callable<Void> catchCallable) throws Exception {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                getLog().debug("attempt {} of {}: {}", attempt, maxAttempts, tryCallable.toString());
                return tryCallable.call();
            } catch (Exception e) {
                if(attempt == maxAttempts) {
                    getLog().warn("attempt {} of {} failed, GIVING UP: {}", attempt, maxAttempts, backoff.toString());
                    getLog().warn("Exception", e);
                    throw e;
                }
                getLog().warn("attempt {} of {} failed, backing off: {}", attempt, maxAttempts, backoff.toString());
                backoff.backoff(attempt);
                catchCallable.call();
            }
        }
        // will never get here, either return successfully or throw an exception
        return null;
    }

    protected Logger getLog() {
        return LOG;
    }

}
