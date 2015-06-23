package org.isisaddons.module.publishmq.routing;

import javax.activation.DataHandler;
import javax.xml.ws.BindingProvider;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import org.apache.isis.applib.services.background.ActionInvocationMemento;

import org.isisaddons.module.publishmq.canonical.demoobject.DemoObjectDto;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.DemoObject;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.DemoObjectService;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.Processed;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.ProcessedResponse;

public class UpdateSoapSubscriber implements Processor {

    private String endpointAddress;
    private DemoObject demoObject;

    public UpdateSoapSubscriber() {
        init();
    }

    private void init() {
        // TODO
        endpointAddress = "http://localhost:7070/soap/SoapSubscriber/DemoObject";

        // although jax-ws proxies are not specified to be thread-safe
        // the CXF implementation is thread-safe in MOST situations (including this)
        // see
        // http://cxf.apache.org/faq.html#FAQ-AreJAX-WSclientproxiesthreadsafe?
        DemoObjectService demoObjectService = new DemoObjectService();
        demoObject = demoObjectService.getDemoObjectOverSOAP();

        BindingProvider provider = (BindingProvider) demoObject;
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

    }

    @Override
    public void process(Exchange exchange) throws Exception {

        final Message message = exchange.getIn();

        final ActionInvocationMemento aim = (ActionInvocationMemento) message.getBody();

        final DataHandler dataHandler = message.getAttachment(EnrichWithCanonicalDto.class.getName());
        final DemoObjectDto demoObjectDto = (DemoObjectDto) dataHandler.getContent();

        final Processed processed = new Processed();


        processed.setName(demoObjectDto.getName());
        processed.setDescription(demoObjectDto.getDescription());

        ProcessedResponse response = demoObject.processed(processed);
        System.out.println(response.getOut());

        System.exit(0);

    }

}
