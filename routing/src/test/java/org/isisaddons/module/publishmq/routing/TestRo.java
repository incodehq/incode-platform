package org.isisaddons.module.publishmq.routing;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.common.collect.ImmutableMap;

import org.junit.Ignore;
import org.junit.Test;

import org.isisaddons.module.publishmq.canonical.demoobject.DemoObjectDto;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestRo  {

    @Ignore // not self-contained, requires webapp to be running
    @Test
    public void process() throws Exception {

        final MediaType mediaType =
                new MediaType("application", "xml",
                        ImmutableMap.of(
                            "profile", "urn:org.restfulobjects:repr-types/object",
                            "x-ro-domain-type", DemoObjectDto.class.getName()));

        final ClientBuilder clientBuilder = ClientBuilder.newBuilder();

        Client client = clientBuilder.build();
        final UriBuilder uriBuilder = UriBuilder.fromUri("http://localhost:7070/restful/objects/{objectType}/{objectInstance}");
        final WebTarget webTarget = client.target( uriBuilder.build("PUBLISH_MQ_DEMO_OBJECT", "1"));

        final Invocation.Builder invocationBuilder = webTarget.request();
        invocationBuilder.accept(mediaType);
        invocationBuilder.header("Authorization", "Basic " + org.apache.cxf.common.util.Base64Utility.encode("sven:pass".getBytes()));

        final Invocation invocation = invocationBuilder.buildGet();
        final Response response = invocation.invoke();

        final int status = response.getStatus();

        final DemoObjectDto entity = response.readEntity(DemoObjectDto.class);
        assertThat(entity.getName(), is("Bar"));

        client.close();
    }
}
