
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.isisaddons.module.publishmq.soapsubscriber.fakeserver;

import java.util.logging.Logger;

import javax.jws.WebService;

import org.isisaddons.module.publishmq.soapsubscriber.demoobject.DemoObject;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.Processed;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.ProcessedResponse;

@WebService(
                      serviceName = "DemoObjectService",
                      portName = "DemoObjectOverSOAP",
                      targetNamespace = "http://isisaddons.org/module/publishmq/soapsubscriber/DemoObject/",
                      wsdlLocation = "classpath:DemoObject.wsdl",
                      endpointInterface = "org.isisaddons.module.publishmq.soapsubscriber.demoobject.DemoObject")

public class DemoObjectOverSOAPImpl implements DemoObject {

    private static final Logger LOG = Logger.getLogger(DemoObjectOverSOAPImpl.class.getName());

    public org.isisaddons.module.publishmq.soapsubscriber.demoobject.ProcessedResponse processed(Processed body) {
        LOG.info("Executing operation processed");
        System.out.println(body);
        try {
            ProcessedResponse response = new ProcessedResponse();
            response.setOut("name=" + body.getName() + ", description=" + body.getDescription());
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
