package org.incode.platform.demo.webapp.app.services.comms.homepage;

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
