package org.isisaddons.module.publishmq.dom.camel;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.TestSupport;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.utils.InteractionDtoUtils;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AddExchangeHeadersTest  extends TestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Before
    public void setUp() throws Exception {
    }

    @DirtiesContext
    @Test
    public void happyCase() throws Exception {

        final InteractionDto interactionDto = InteractionDtoUtils
                .fromXml(getClass(), "memberInteractionMementoDto-example.xml", Charsets.UTF_8);
        final ImmutableMap<String, Object> aimHeader = ImmutableMap.<String,Object>builder()
                .put("transactionId", interactionDto.getTransactionId())
                .put("execution$id", interactionDto.getExecution().getId())
                .put("execution$sequence", interactionDto.getExecution().getSequence())
                .put("execution$memberIdentifier", interactionDto.getExecution().getMemberIdentifier())
                .put("execution$user", interactionDto.getExecution().getUser())
                .put("execution$metrics$timings$startedAt", interactionDto.getExecution().getMetrics().getTimings().getStartedAt())
                .build();

        resultEndpoint.expectedHeaderReceived("ixn", aimHeader);
        resultEndpoint.expectedBodiesReceived(interactionDto);

        template.sendBody(interactionDto);

        resultEndpoint.assertIsSatisfied();
    }

}