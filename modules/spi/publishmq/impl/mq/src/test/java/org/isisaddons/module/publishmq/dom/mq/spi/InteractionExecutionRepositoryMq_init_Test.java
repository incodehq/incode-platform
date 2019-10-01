package org.isisaddons.module.publishmq.dom.mq.spi;

import java.util.Map;

import com.google.common.collect.Maps;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.isisaddons.module.publishmq.dom.servicespi.PublisherServiceUsingActiveMq;

import static com.google.common.collect.ImmutableMap.of;

public class InteractionExecutionRepositoryMq_init_Test {

    private InteractionExecutionRepositoryMq target;

    Boolean fallThroughToConnect = false;

    @Before
    public void setUp() throws Exception {
        target = new InteractionExecutionRepositoryMq() {
            @Override void connect() {
                fallThroughToConnect = true;
            }
        };
    }

    @Test
    public void when_enabled() throws Exception {

        // given
        Map<String, String> properties = of(PublisherServiceUsingActiveMq.KEY_ENABLED, "true");

        // when
        target.init(properties);

        // then
        Assertions.assertThat(fallThroughToConnect).isTrue();
    }

    @Test
    public void when_not_specified() throws Exception {

        // given
        Map<String, String> properties = Maps.newHashMap();

        // when
        target.init(properties);

        // then
        Assertions.assertThat(fallThroughToConnect).isTrue();
    }

    @Test
    public void when_disabled() throws Exception {
        // given
        Map<String, String> properties = of(PublisherServiceUsingActiveMq.KEY_ENABLED, "false");

        // when
        target.init(properties);

        // then
        Assertions.assertThat(fallThroughToConnect).isFalse();
    }

}