package domainapp.appdefn.services.homepage.comms;

import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotesRepository;
import org.incode.domainapp.example.dom.demo.dom.invoice2.DemoInvoice2;
import org.incode.domainapp.example.dom.demo.dom.invoice2.DemoInvoice2Repository;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "domainapp.appdefn.services.homepage.comms.HomePageViewModel"
)
public class HomePageViewModel {

    public TranslatableString title() {
        return TranslatableString.tr("{cus} customers, {inv} invoices", "cus", getDemoCustomers().size(), "inv", getDemoInvoices().size());
    }

    public List<DemoObjectWithNotes> getDemoCustomers() {
        return demoCustomerRepository.listAll();
    }

    public List<DemoInvoice2> getDemoInvoices() {
        return demoInvoiceRepository.listAll();
    }

    @javax.inject.Inject
    DemoObjectWithNotesRepository demoCustomerRepository;

    @javax.inject.Inject
    DemoInvoice2Repository demoInvoiceRepository;

}
