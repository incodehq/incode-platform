package org.incode.example.docfragment.demo.shared.invoicewithatpath.dom;

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
        objectType = "docFragmentDemoShared.DocFragInvoiceMenu"
)
@DomainServiceLayout(
        named = "Dummy",
        menuOrder = "20.2"
)
public class DocFragInvoiceMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DocFragInvoice> listAllDemoInvoices() {
        return docFragInvoiceRepository.listAll();
    }


    @javax.inject.Inject
    DocFragInvoiceRepository docFragInvoiceRepository;

}
