package domainapp.example.embeddedcamel.processor;

import java.net.URI;

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

import org.apache.isis.schema.ixn.v1.InteractionDto;

import org.isisaddons.module.publishmq.dom.statusclient.StatusMessage;
import org.isisaddons.module.publishmq.dom.statusclient.StatusMessageClient;

import org.incode.domainapp.example.canonical.SimpleObjectDto;

public class AttachSimpleObjectDto implements Processor {

    public static final MediaType MEDIA_TYPE = new MediaType("application", "xml",
            ImmutableMap.of(
                    "profile", "urn:org.restfulobjects:repr-types/object",
                    "x-ro-domain-type", SimpleObjectDto.class.getName()));

    private final ClientBuilder clientBuilder;
    private UriBuilder objectInstanceUriBuilder;

    private StatusMessageClient statusMessageClient;

    public AttachSimpleObjectDto() {
        clientBuilder = ClientBuilder.newBuilder();
    }

    private String username;
    private String password;
    private String base;

    public void setBase(final String base) {
        this.base = base;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void init() {
        objectInstanceUriBuilder = UriBuilder.fromUri(base + "objects/{objectType}/{objectInstance}");
        statusMessageClient = new StatusMessageClient(base, username, password);
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Message inMessage = exchange.getIn();
        final InteractionDto interactionDto = (InteractionDto) inMessage.getBody();
        final String objectType = interactionDto.getExecution().getTarget().getType();
        final String objectIdentifier = interactionDto.getExecution().getTarget().getId();

        if(!"simple.SimpleObject".equals(objectType)) {
            throw new IllegalArgumentException(String.format(
                    "Expected target's object type to be 'simple.SimpleObject', instead was '%s'", objectType));
        }

        final String transactionId = interactionDto.getTransactionId();

        statusMessageClient.log(
                StatusMessage.builder(transactionId, "Retrieving object")
                             .withOid(objectType, objectIdentifier));

        Client client = clientBuilder.build();
        try {
            final URI uri = objectInstanceUriBuilder.build(objectType, objectIdentifier);
            final WebTarget webTarget = client.target(uri);

            final Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept(MEDIA_TYPE);
            invocationBuilder.header("Authorization", "Basic " + encode(username, password));
            final Invocation invocation = invocationBuilder.buildGet();

            final Response response = invocation.invoke();

            final int status = response.getStatus();
            if(status != 200) {
                final String exception = response.readEntity(String.class);

                statusMessageClient.log(
                        StatusMessage.builder(transactionId, "Failed to retrieve object representation")
                                .withUri(uri)
                                .withStatus(status)
                                .withDetail(exception));

                throw new RuntimeException(exception);
            }

            statusMessageClient.log(StatusMessage.builder(transactionId, "Retrieve object").withUri(uri));

            final SimpleObjectDto entity = response.readEntity(SimpleObjectDto.class);
            inMessage.setHeader(SimpleObjectDto.class.getName(), entity);

        } finally {
            closeQuietly(client);
        }

    }

    private static String encode(final String username, final String password) {
        return java.util.Base64.getEncoder().encodeToString(asBytes(username, password));
    }

    private static byte[] asBytes(final String username, final String password) {
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
