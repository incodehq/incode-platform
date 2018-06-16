package org.incode.extended.integtests.examples.communications.dom.communications.dom.demowithnotes;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import org.incode.extended.integtests.examples.communications.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.extended.integtests.examples.communications.demo.dom.invoice.DemoInvoice;
import org.incode.extended.integtests.examples.communications.demo.dom.invoice.DemoInvoiceRepository;

@Mixin(method = "coll")
public class DemoObjectWithNotes_invoices {
    private final DemoObjectWithNotes demoCustomer;

    public DemoObjectWithNotes_invoices(final DemoObjectWithNotes demoCustomer) {
        this.demoCustomer = demoCustomer;
    }

    public static class DomainEvent extends ActionDomainEvent<DemoObjectWithNotes> {
    }

    @Action(semantics = SemanticsOf.SAFE, domainEvent = DomainEvent.class)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<DemoInvoice> coll() {
        return invoiceRepository.findByCustomer(demoCustomer);
    }

    @Inject
    DemoInvoiceRepository invoiceRepository;
}
