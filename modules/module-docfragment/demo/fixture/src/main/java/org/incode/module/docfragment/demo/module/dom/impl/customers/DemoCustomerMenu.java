package org.incode.module.docfragment.demo.module.dom.impl.customers;

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
        repositoryFor = DemoCustomer.class
)
@DomainServiceLayout(
        named = "Demo Customers",
        menuOrder = "10"
)
public class DemoCustomerMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DemoCustomer> listAll() {
        return demoCustomerRepository.listAll();
    }


    @javax.inject.Inject
    DemoCustomerRepository demoCustomerRepository;

}
