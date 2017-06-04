/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.communications.demo.application.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomer;
import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomerRepository;
import org.incode.module.communications.demo.module.dom.impl.invoices.DemoInvoice;
import org.incode.module.communications.demo.module.dom.impl.invoices.DemoInvoiceRepository;

@ViewModel
public class HomePageViewModel {

    public TranslatableString title() {
        return TranslatableString.tr("{cus} customers, {inv} invoices", "cus", getDemoCustomers().size(), "inv", getDemoInvoices().size());
    }

    public List<DemoCustomer> getDemoCustomers() {
        return demoCustomerRepository.listAll();
    }

    public List<DemoInvoice> getDemoInvoices() {
        return demoInvoiceRepository.listAll();
    }

    @javax.inject.Inject
    DemoCustomerRepository demoCustomerRepository;

    @javax.inject.Inject
    DemoInvoiceRepository demoInvoiceRepository;

}
