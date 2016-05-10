package org.isisaddons.module.publishmq.fixture.routing;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import org.isisaddons.module.publishmq.canonical.demoobject.DemoObjectDto;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObject;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObjectService;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.PostResponse;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.Update;

public class PostToExternalWebServiceUsingSoap implements Processor {

    public static final String HEADER_EXTERNAL_SYSTEM_INTERNAL_ID = "externalSystemInternalId";

    private String endpointAddressBase;
    public void setEndpointAddressBase(final String endpointAddressBase) {
        this.endpointAddressBase = endpointAddressBase;
    }
    private String endpointAddressSuffix;
    public void setEndpointAddressSuffix(final String endpointAddressSuffix) {
        this.endpointAddressSuffix = endpointAddressSuffix;
    }

    /**
     * Derived from {@link #setEndpointAddressBase(String) base} and {@link #setEndpointAddressSuffix(String) suffix}.
     */
    String getEndpointAddress() {
        return endpointAddressBase + endpointAddressSuffix;
    }

    private DemoObject demoObject;

    public void init() {
        // although jax-ws proxies are not specified to be thread-safe
        // the CXF implementation is thread-safe in MOST situations (including this)
        // see http://cxf.apache.org/faq.html#FAQ-AreJAX-WSclientproxiesthreadsafe?
        DemoObjectService demoObjectService = new DemoObjectService();
        demoObject = demoObjectService.getDemoObjectOverSOAP();

        BindingProvider provider = (BindingProvider) demoObject;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndpointAddress());
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        final Message message = exchange.getIn();

        Map<String, Object> aimHeader = (Map<String, Object>) message.getHeader("ixn");
        final String messageId = (String) aimHeader.get("execution$id");

        final DemoObjectDto demoObjectDto =
                (DemoObjectDto) message.getHeader(DemoObjectDto.class.getName());

        final Update update = new Update();

        update.setMessageId(messageId);
        update.setName(demoObjectDto.getName());
        update.setDescription(demoObjectDto.getDescription());

        PostResponse response = demoObject.post(update);

        final int externalSystemInternalId = response.getInternalId();
        // unused
        final String responseTransactionId = response.getMessageId();

        message.setHeader(HEADER_EXTERNAL_SYSTEM_INTERNAL_ID, externalSystemInternalId);
    }

}
