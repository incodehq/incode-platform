package org.incode.module.docfragment.demo.application.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.module.docfragment.demo.module.dom.impl.customers.DemoCustomer;
import org.incode.module.docfragment.demo.module.dom.impl.customers.DemoCustomerRepository;
import org.incode.module.docfragment.demo.module.dom.impl.invoices.DemoInvoice;
import org.incode.module.docfragment.demo.module.dom.impl.invoices.DemoInvoiceRepository;
import org.incode.module.docfragment.dom.impl.DocFragment;
import org.incode.module.docfragment.dom.impl.DocFragmentRepository;

@ViewModel
public class HomePageViewModel {

    public TranslatableString title() {
        return TranslatableString.tr("{num} doc fragments", "num", getDocFragmentObjects().size());
    }

    public List<DocFragment> getDocFragmentObjects() {
        return docfragmentRepository.listAll();
    }

    public List<DemoCustomer> getDemoCustomers() {
        return demoCustomerRepository.listAll();
    }

    public List<DemoInvoice> getDemoInvoice() {
        return demoInvoiceRepository.listAll();
    }

    @javax.inject.Inject
    DocFragmentRepository docfragmentRepository;

    @javax.inject.Inject
    DemoCustomerRepository demoCustomerRepository;

    @javax.inject.Inject
    DemoInvoiceRepository demoInvoiceRepository;


}
