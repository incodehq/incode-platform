package org.incode.domainapp.example.dom.demo.dom.demowithnotes;

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
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDemo.DemoObjectWithNotesMenu"
)
@DomainServiceLayout(
        named = "Demo Customers",
        menuOrder = "10"
)
public class DemoObjectWithNotesMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DemoObjectWithNotes> listAll() {
        return demoCustomerRepository.listAll();
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<DemoObjectWithNotes> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return demoCustomerRepository.findByName(name);
    }


    public static class CreateDomainEvent extends ActionDomainEvent<DemoObjectWithNotesMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public DemoObjectWithNotes create(
            @ParameterLayout(named="Name")
            final String name) {
        return demoCustomerRepository.create(name);
    }


    @javax.inject.Inject
    DemoObjectWithNotesRepository demoCustomerRepository;

}
