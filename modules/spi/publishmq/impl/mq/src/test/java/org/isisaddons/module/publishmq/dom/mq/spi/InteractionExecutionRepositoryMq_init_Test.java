package org.isisaddons.module.publishmq.dom.mq.spi;

import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.isisaddons.module.publishmq.dom.servicespi.PublisherServiceUsingActiveMqStatusProvider;

public class InteractionExecutionRepositoryMq_init_Test {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    private PublisherServiceUsingActiveMqStatusProvider mockStatusProvider;

    private InteractionExecutionRepositoryMq target;
    Boolean fallThroughToConnect = false;

    @Before
    public void setUp() throws Exception {
        target = new InteractionExecutionRepositoryMq() {
            @Override void connect() {
                fallThroughToConnect = true;
            }
        };
        target.statusProvider = mockStatusProvider;
    }

    @Test
    public void when_enabled() throws Exception {

        // expecting
        context.checking(new Expectations() {{
            allowing(mockStatusProvider).isEnabled();
            will(returnValue(true));
        }});

        // when
        target.init(new HashMap<>());

        // then
        Assertions.assertThat(fallThroughToConnect).isTrue();
    }

    @Test
    public void when_disabled() throws Exception {

        // given
        context.checking(new Expectations() {{
            allowing(mockStatusProvider).isEnabled();
            will(returnValue(false));
        }});


        // when
        target.init(new HashMap<>());

        // then
        Assertions.assertThat(fallThroughToConnect).isFalse();
    }

}