/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.isisaddons.module.publishmq.soapsubscriber;

import java.io.File;
import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.isisaddons.module.publishmq.soapsubscriber.demoobject.DemoObject;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.DemoObjectService;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.Processed;
import org.isisaddons.module.publishmq.soapsubscriber.demoobject.ProcessedResponse;

public class DemoObjectClient {
    protected DemoObjectClient() {
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
                "http://localhost:7070/soap/SoapSubscriber/DemoObject");

        // test the service

        final Processed processed = new Processed();
        processed.setName("Fred");
        processed.setDescription("Freddie Mercury");
        ProcessedResponse response = demoObject.processed(processed);
        System.out.println(response.getOut());

        System.exit(0);
    }
}
