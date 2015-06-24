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

import org.isisaddons.module.publishmq.dom.ActionInvocationMementoUtils;
import org.isisaddons.module.publishmq.dom.canonical.aim.ActionInvocationMementoDto;

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

        final ActionInvocationMementoDto aim = ActionInvocationMementoUtils.fromXml(getClass(), "actionInvocationMementoDto-example.xml", Charsets.UTF_8);
        final ActionInvocationMementoDto.Metadata metadata = aim.getMetadata();
        final ImmutableMap<String, Object> aimHeader = ImmutableMap.<String,Object>builder()
                .put("messageId", metadata.getTransactionId() + ":" + metadata.getSequence())
                .put("transactionId", metadata.getTransactionId())
                .put("sequence", metadata.getSequence())
                .put("actionIdentifier", metadata.getActionIdentifier())
                .put("timestamp", metadata.getTimestamp())
                .put("user", metadata.getUser())
                .build();

        resultEndpoint.expectedHeaderReceived("aim", aimHeader);
        resultEndpoint.expectedBodiesReceived(aim);

        template.sendBody(aim);

        resultEndpoint.assertIsSatisfied();
    }

}