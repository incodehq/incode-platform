package org.incode.example.communications.demo.usage.contributions.demowithnotes;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsInvoiceRepository;
import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsCustomer;
import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsInvoice;

@Mixin(method = "coll")
public class CommsCustomer_invoices {
    private final CommsCustomer demoCustomer;

    public CommsCustomer_invoices(final CommsCustomer demoCustomer) {
        this.demoCustomer = demoCustomer;
    }

    public static class DomainEvent extends ActionDomainEvent<CommsCustomer> {
    }

    @Action(semantics = SemanticsOf.SAFE, domainEvent = DomainEvent.class)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<CommsInvoice> coll() {
        return invoiceRepository.findByCustomer(demoCustomer);
    }

    @Inject
    CommsInvoiceRepository invoiceRepository;
}
