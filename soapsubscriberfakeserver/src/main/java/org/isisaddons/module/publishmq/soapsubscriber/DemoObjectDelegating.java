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

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceContext;

import org.isisaddons.module.publishmq.soapsubscriber.demoobject.DemoObject;

/**
 * Intended to wrap a mock implementation.
 */
public class DemoObjectDelegating implements DemoObject {
    
    /**
     * The WebServiceContext can be used to retrieve special attributes like the 
     * user principal. Normally it is not needed
     */
    @Resource
    WebServiceContext wsContext;

    private final DemoObject delegate;
    
    public DemoObjectDelegating(DemoObject delegate) {
        this.delegate = delegate;
    }

    @Override public String processed(@WebParam(name = "name", targetNamespace = "") final String name, @WebParam(name = "description", targetNamespace = "") final String description) {
        return delegate.processed(name, description);
    }
}
