package org.incode.domainapp.example.dom.demo.dom.demowithblob;

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
        objectType = "exampleWktPdfjs.DemoObjectMenu"
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.4"
)
public class DemoObjectWithBlobMenu {


    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<DemoObjectWithBlob> listAllDemoObjectsWithBlob() {
        return repositoryService.allInstances(DemoObjectWithBlob.class);
    }

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public DemoObjectWithBlob createDemoObjectWithBlob(
            @ParameterLayout(named = "Name")
            final String name) {
        final DemoObjectWithBlob obj = repositoryService.instantiate(DemoObjectWithBlob.class);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    @javax.inject.Inject
    RepositoryService repositoryService;

}
