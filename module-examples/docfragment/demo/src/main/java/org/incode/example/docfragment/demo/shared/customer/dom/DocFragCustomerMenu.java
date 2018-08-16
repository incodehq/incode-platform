package org.incode.example.docfragment.demo.shared.customer.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "docFragmentDemoShared.DocFragCustomerMenu"
)
@DomainServiceLayout(
        named = "Dummy",
        menuOrder = "20.1"
)
public class DocFragCustomerMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DocFragCustomer> listAllDemoCustomers() {
        return docFragCustomerRepository.listAll();
    }


    @javax.inject.Inject
    DocFragCustomerRepository docFragCustomerRepository;

}
