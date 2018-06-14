package org.isisaddons.module.command.replay.impl;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.junit.Ignore;
import org.junit.Test;

import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.schema.cmd.v1.CommandsDto;

import org.incode.module.jaxrsclient.dom.JaxRsClient;
import org.incode.module.jaxrsclient.dom.JaxRsResponse;

public class RunBackgroundCommandsWithReplicationAndReplayJob_Test {

    @Ignore
    @Test
    public void testing_the_unmarshalling() throws Exception {
        JaxRsClient.Default jaxRsClient = new JaxRsClient.Default();
        final UriBuilder uriBuilder = UriBuilder.fromUri(
                        String.format(
                        "%s%s?batchSize=%d",
                        "http://localhost:8080/restful/", CommandFetcher.URL_SUFFIX, 10)
        );
        URI uri = uriBuilder.build();
        JaxRsResponse invoke = jaxRsClient.get(uri, CommandsDto.class, JaxRsClient.ReprType.ACTION_RESULT, "sven", "pass");
        CommandsDto entity = invoke.readEntity(CommandsDto.class);
        System.out.println(new JaxbService.Simple().toXml(entity));
    }
}