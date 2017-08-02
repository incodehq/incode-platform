package org.incode.domainapp.example.dom.wkt.pdfjs.dom.demo;

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
        nature = NatureOfService.VIEW,
        repositoryFor = DemoObject.class
)
@DomainServiceLayout(
        named = "Demo Objects",
        menuOrder = "10"
)
public class DemoObjectMenu {


    //region > listAll (action)

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

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public DemoObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final DemoObject obj = repositoryService.instantiate(DemoObject.class);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    @javax.inject.Inject
    RepositoryService repositoryService;

}
