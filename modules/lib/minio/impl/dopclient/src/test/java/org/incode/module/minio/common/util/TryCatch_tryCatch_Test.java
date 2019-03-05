package org.incode.module.minio.common.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class TryCatch_tryCatch_Test {

    @Test
    public void succeeds_first_time() throws Exception {

        final Object returnedValue = new Object();

        final Object returned = new TryCatch().tryCatch(() -> returnedValue, () -> null);

        assertThat(returned).isEqualTo(returnedValue);
    }

    @Test
    public void succeeds_last_time() throws Exception {

        final TryCatch tryCatch = new TryCatch(3, attempt -> {});

        final Object returnedValue = new Object();
        AtomicInteger i = new AtomicInteger(1);
        StringBuilder log = new StringBuilder();

        final Object returned = tryCatch.tryCatch(
                () -> {
                    if(i.getAndIncrement() < 3) {
                        log.append("Failing\n");
                        throw new RuntimeException("failed!");
                    }
                    log.append("Succeeding\n");
                    return returnedValue;
                },
                () -> {
                    log.append("Recovering\n");
                    return null;
                });

        assertThat(returned).isEqualTo(returnedValue);
        assertThat(log.toString()).isEqualTo(
                "Failing\n" +
                "Recovering\n" +
                "Failing\n" +
                "Recovering\n" +
                "Succeeding\n"
        );
    }


    @Test
    public void fails_every_time() {

        StringBuilder log = new StringBuilder();

        final TryCatch tryCatch = new TryCatch(3, attempt -> {});
        try {
            tryCatch.tryCatch(
                () -> {
                    log.append("Failing\n");
                    throw new RuntimeException("failed!");
                },
                () -> {
                    log.append("Recovering\n");
                    return null;
                });
            fail("Should not get here...");
        } catch (Exception ex) {
            assertThat(ex.getMessage()).isEqualTo("failed!");
            assertThat(log.toString()).isEqualTo(
                    "Failing\n" +
                    "Recovering\n" +
                    "Failing\n" +
                    "Recovering\n" +
                    "Failing\n"
            );
        }

    }
}