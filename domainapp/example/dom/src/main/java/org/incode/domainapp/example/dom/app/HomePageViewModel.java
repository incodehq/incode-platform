package org.incode.domainapp.example.dom.app;

import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;

import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomer;
import org.incode.domainapp.example.dom.demo.dom.customer.DemoCustomerMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPathMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlob;
import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlobMenu;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotesRepository;
import org.incode.domainapp.example.dom.demo.dom.invoice2.DemoInvoice2;
import org.incode.domainapp.example.dom.demo.dom.invoice2.DemoInvoice2Repository;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "org.incode.domainapp.example.dom.app.HomePageViewModel"
)
public class HomePageViewModel {

    public List<DemoCustomer> getDemoCustomers() {
        return demoCustomerMenu.listAll();
    }

    public List<DemoObjectWithNotes> getDemoObjectWithNotes() {
        return demoObjectWithNotesRepository.listAll();
    }

    public List<DemoObjectWithAtPath> getDemoObjectsWithAtPath() {
        return demoObjectWithAtPathMenu.listAllDemoObjectsWithAtPath();
    }

    public List<DemoObjectWithBlob> getDemoObjectsWithBlob() {
        return demoObjectWithBlobMenu.listAllDemoObjectsWithBlob();
    }

    public List<DemoInvoice2> getDemoInvoices() {
        return demoInvoiceRepository.listAll();
    }

    @javax.inject.Inject
    DemoCustomerMenu demoCustomerMenu;

    @javax.inject.Inject
    DemoObjectWithAtPathMenu demoObjectWithAtPathMenu;

    @javax.inject.Inject
    DemoObjectWithBlobMenu demoObjectWithBlobMenu;

    @javax.inject.Inject
    DemoObjectWithNotesRepository demoObjectWithNotesRepository;

    @javax.inject.Inject
    DemoInvoice2Repository demoInvoiceRepository;

}
