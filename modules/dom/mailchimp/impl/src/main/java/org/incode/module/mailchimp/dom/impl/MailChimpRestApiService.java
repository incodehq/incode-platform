package org.incode.module.mailchimp.dom.impl;

import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.core.commons.config.IsisConfiguration;

@DomainService(nature = NatureOfService.DOMAIN)
public class MailChimpRestApiService {

    @Programmatic
    public String callRestApi(final String href, final String method, final String body) {
        try {
            javax.ws.rs.client.Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, getConnectTimeOut());
            client.property(ClientProperties.READ_TIMEOUT,    getReadTimeOut());
            String resourceString = href;
            WebTarget webResource2 = client.target(resourceString);
            Invocation.Builder invocationBuilder =
                    webResource2
                            .request(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Basic " + encode("apikey", getApiSecret()));
            Response response;
            switch (method){

            case HttpMethod.GET:
                response = invocationBuilder.get();
                if (response.getStatus() != 200) {
                    throw new RuntimeException("" + response.getStatus());
                }
                return response.readEntity(String.class);

            case HttpMethod.POST:
                response = invocationBuilder.post(Entity.json(body));
                if (response.getStatus() != 200) {
                    throw new RuntimeException("" + response.getStatus() + " " + response.readEntity(String.class));
                }
                return response.readEntity(String.class);

            case HttpMethod.DELETE:
                response = invocationBuilder.delete();
                if (response.getStatus() != 204) {
                    throw new RuntimeException("" + response.getStatus());
                } else {
                    return "deleted";
                }

            default:
                return null;
            }
        } catch (Exception e) {
            messageService.warnUser(e.getMessage());
            return null;
        }
    }

    private String getApiSecret() {
        return this.configuration.getString("isis.service.mailchimp.secret");
    }

    private Integer getConnectTimeOut() {
        String timeoutStr = this.configuration.getString("isis.service.mailchimp.connect-timeout");
        return timeoutStr!=null ? Integer.valueOf(timeoutStr) : 2000;
    }

    private Integer getReadTimeOut() {
        String timeoutStr = this.configuration.getString("isis.service.mailchimp.read-timeout");
        return timeoutStr!=null ? Integer.valueOf(timeoutStr) : 30000;
    }

    private static String encode(final String username, final String password) {
        return org.apache.cxf.common.util.Base64Utility.encode(asBytes(username, password));
    }

    private static byte[] asBytes(final String username, final String password) {
        return String.format("%s:%s", username, password).getBytes();
    }


    @Inject private MessageService messageService;

    @Inject private IsisConfiguration configuration;

}
