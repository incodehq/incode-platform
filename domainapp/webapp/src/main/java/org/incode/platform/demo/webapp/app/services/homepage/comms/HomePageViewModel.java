package org.incode.platform.demo.webapp.app.services.homepage.comms;

import java.util.List;

import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.modules.exampledom.module.communications.dom.demo.DemoCustomer;
import domainapp.modules.exampledom.module.communications.dom.demo.DemoCustomerRepository;
import domainapp.modules.exampledom.module.communications.dom.demo2.DemoInvoice;
import domainapp.modules.exampledom.module.communications.dom.demo2.DemoInvoiceRepository;

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
