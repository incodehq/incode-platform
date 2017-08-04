package org.incode.domainapp.example.dom.demo.dom.demo;

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
        objectType = "exampleDemo.DemoObjectMenu"
)
@DomainServiceLayout(
        named = "Demo Objects",
        menuOrder = "10"
)
public class DemoObjectMenu {



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DemoObject> listAll() {
        return repository.listAll();
    }



    @MemberOrder(sequence = "2")
    public DemoObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        return repository.create(name);
    }


    @javax.inject.Inject 
    DemoObjectRepository repository;


}
