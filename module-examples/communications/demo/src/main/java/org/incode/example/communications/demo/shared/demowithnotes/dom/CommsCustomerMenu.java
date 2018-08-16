package org.incode.example.communications.demo.shared.demowithnotes.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "communicationsDemoShared.CommsCustomerMenu"
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.5"
)
public class CommsCustomerMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<CommsCustomer> listAllDemoObjectsWithNotes() {
        return commsCustomerRepository.listAll();
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<CommsCustomer> findDemoObjectsWithNotesByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return commsCustomerRepository.findByName(name);
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @MemberOrder(sequence = "3")
    public CommsCustomer createDemoObjectWithNotes(
            @ParameterLayout(named="Name")
            final String name) {
        return commsCustomerRepository.create(name);
    }


    @javax.inject.Inject
    CommsCustomerRepository commsCustomerRepository;

}
