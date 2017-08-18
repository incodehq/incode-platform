package org.incode.domainapp.example.dom.demo.dom.invoice;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * as used by DocFragment
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDemo.DemoInvoiceMenu"
)
@DomainServiceLayout(
        named = "Demo Invoices",
        menuOrder = "10"
)
public class DemoInvoiceMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DemoInvoice> listAllDemoInvoices() {
        return demoInvoiceRepository.listAll();
    }


    @javax.inject.Inject
    DemoInvoiceRepository demoInvoiceRepository;

}
