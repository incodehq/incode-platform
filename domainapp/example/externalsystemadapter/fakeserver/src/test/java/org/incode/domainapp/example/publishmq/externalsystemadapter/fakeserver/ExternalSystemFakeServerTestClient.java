package org.incode.domainapp.example.publishmq.externalsystemadapter.fakeserver;

import java.io.File;
import java.net.URL;
import java.util.UUID;

import javax.xml.ws.BindingProvider;

import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObject;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.DemoObjectService;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.PostResponse;
import org.isisaddons.module.publishmq.externalsystemadapter.demoobject.Update;

public class ExternalSystemFakeServerTestClient {
    protected ExternalSystemFakeServerTestClient() {
    }
    
    public static void main(String args[]) throws Exception {
        DemoObjectService demoObjectService;
        if (args.length != 0 && args[0].length() != 0) {
            File wsdlFile = new File(args[0]);
            URL wsdlURL;
            if (wsdlFile.exists()) {
                wsdlURL = wsdlFile.toURI().toURL();
            } else {
                wsdlURL = new URL(args[0]);
            }
            // Create the service client with specified wsdlurl
            demoObjectService = new DemoObjectService(wsdlURL);
        } else {
            // Create the service client with its default wsdlurl
            demoObjectService = new DemoObjectService();
        }

        DemoObject demoObject = demoObjectService.getDemoObjectOverSOAP();

        BindingProvider provider = (BindingProvider)demoObject;
        provider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                "http://localhost:7070/soap/ExternalSystemAdapter/DemoObject");

        // test the service

        final Update update = new Update();
        update.setMessageId(UUID.randomUUID().toString());
        update.setName("Fred");
        update.setDescription("Freddie Mercury");

        PostResponse response = demoObject.post(update);

        System.out.println("internal Id: " + response.getInternalId());
        System.out.println("message Id: " + response.getMessageId());

        System.exit(0);
    }
}
