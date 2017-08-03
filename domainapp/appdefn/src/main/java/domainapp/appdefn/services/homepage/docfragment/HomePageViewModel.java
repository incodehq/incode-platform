package domainapp.appdefn.services.homepage.docfragment;

import java.util.List;

import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.domainapp.example.dom.dom.docfragment.dom.democust.DemoCustomer;
import org.incode.domainapp.example.dom.dom.docfragment.dom.democust.DemoCustomerRepository;
import org.incode.domainapp.example.dom.dom.docfragment.dom.demoinvoice.DemoInvoice;
import org.incode.domainapp.example.dom.dom.docfragment.dom.demoinvoice.DemoInvoiceRepository;
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
