package org.isisaddons.module.publishmq.dom.outboxclient;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Assume;
import org.junit.Test;

import org.apache.isis.applib.util.JaxbUtil;
import org.apache.isis.schema.ixn.v1.InteractionDto;

public class OutboxClient_pending_E2eTest {

    private OutboxClient outboxClient;

    @org.junit.Before
    public void assuming() throws Exception {
        Assume.assumeTrue(System.getProperty("e2e.test") != null);
    }

    @org.junit.Before
    public void setUp() throws Exception {
        outboxClient = new OutboxClient();
        outboxClient.setBase("http://localhost:8080/restful/");
        outboxClient.setUsername("sven");
        outboxClient.setPassword("pass");

        outboxClient.init();
    }

    @Test
    public void happy_case() throws Exception {

        final List<InteractionDto> pending = outboxClient.pending();

        Assertions.assertThat(pending).isNotNull();

        System.out.println("");
        System.out.println("initially...");
        System.out.println("");
        pending.forEach(dto -> System.out.println(JaxbUtil.toXml(dto)));


        // when
        System.out.println("");
        System.out.println("deleting first...");
        System.out.println("");
        pending.stream().findFirst().ifPresent(x -> outboxClient.delete(x.getTransactionId(), x.getExecution().getSequence()));

        //

        System.out.println("");
        System.out.println("afterwards...");
        System.out.println("");
        final List<InteractionDto> pending2 = outboxClient.pending();
        Assertions.assertThat(pending2).isNotNull();

        pending2.forEach(dto -> System.out.println(JaxbUtil.toXml(dto)));

        //
        System.out.flush();

    }

}