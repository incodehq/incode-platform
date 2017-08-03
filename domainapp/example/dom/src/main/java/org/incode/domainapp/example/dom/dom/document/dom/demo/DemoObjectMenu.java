package org.incode.domainapp.example.dom.dom.document.dom.demo;

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
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDomDocument.DemoObjectMenu",
        repositoryFor = DemoObject.class
)
@DomainServiceLayout(
        named = "Document Demo Objects",
        menuOrder = "10"
)
public class DemoObjectMenu {



    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<DemoObject> listAll() {
        return repositoryService.allInstances(DemoObject.class);
    }



    @MemberOrder(sequence = "2")
    public DemoObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final DemoObject obj = repositoryService.instantiate(DemoObject.class);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

}
