package org.isisaddons.module.publishmq.routing;

import javax.activation.DataHandler;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.common.collect.ImmutableMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import org.isisaddons.module.publishmq.canonical.demoobject.DemoObjectDto;
import org.isisaddons.module.publishmq.dom.canonical.aim.ActionInvocationMementoDto;

public class EnrichWithCanonicalDto implements Processor {

    private final ClientBuilder clientBuilder;
    public static final MediaType MEDIA_TYPE = new MediaType("application", "xml",
            ImmutableMap.of(
                    "profile", "urn:org.restfulobjects:repr-types/object",
                    "x-ro-domain-type", DemoObjectDto.class.getName()));

    private UriBuilder uriBuilder;

    private String username;
    private String password;
    private String base;

    public EnrichWithCanonicalDto() {
        clientBuilder = ClientBuilder.newBuilder();

        init();
    }

    private void init() {

        // TODO: need to parameterize
        base = "http://localhost:7070/restful/";
        username = "sven";
        password = "pass";

        uriBuilder = UriBuilder.fromUri(base
                + "objects/{objectType}/{objectInstance}");

    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Message inMessage = exchange.getIn();
        final ActionInvocationMementoDto aim = (ActionInvocationMementoDto) inMessage.getBody();
        final String objectType = aim.getMetadata().getTarget().getObjectType();
        final String objectIdentifier = aim.getMetadata().getTarget().getObjectIdentifier();

        if(!"PUBLISH_MQ_DEMO_OBJECT".equals(objectType)) {
            throw new IllegalArgumentException("Expected target's object type to be 'PUBLISH_MQ_DEMO_OBJECT', instead was '" + objectType + "'");
        }

        Client client = clientBuilder.build();
        try {
            final WebTarget webTarget = client.target(uriBuilder.build(objectType, objectIdentifier));

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept(MEDIA_TYPE);
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));
            final Invocation invocation = invocationBuilder.buildGet();

            final Response response = invocation.invoke();

            final int status = response.getStatus();
            if(status != 200) {
                final String s = response.readEntity(String.class);
                // TODO: tidy up
                throw new RuntimeException(s);
            }

            final DemoObjectDto entity = response.readEntity(DemoObjectDto.class);

            final DataHandler entityDataHandler = new DataHandler(entity, response.getMediaType().toString());
            inMessage.addAttachment(EnrichWithCanonicalDto.class.getName(), entityDataHandler);

        } finally {
            closeQuietly(client);
        }

    }

    protected String encode(final String username, final String password) {
        return org.apache.cxf.common.util.Base64Utility.encode(asBytes(username, password));
    }

    protected byte[] asBytes(final String username, final String password) {
        return String.format("%s:%s", username, password).getBytes();
    }

    private static void closeQuietly(final Client client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch(Exception ex) {
            // ignore so as to avoid overriding any pending exceptions in calling 'finally' block.
        }
    }

}
