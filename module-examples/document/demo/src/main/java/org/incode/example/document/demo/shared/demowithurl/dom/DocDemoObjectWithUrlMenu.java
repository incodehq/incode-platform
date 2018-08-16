package org.incode.example.document.demo.shared.demowithurl.dom;

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
        objectType = "documentDemoShared.DocDemoObjectWithUrlMenu",
        repositoryFor = DocDemoObjectWithUrl.class
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.6"
)
public class DocDemoObjectWithUrlMenu {



    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<DocDemoObjectWithUrl> listAllDemoObjectsWithUrl() {
        return repositoryService.allInstances(DocDemoObjectWithUrl.class);
    }



    @MemberOrder(sequence = "2")
    public DocDemoObjectWithUrl createDemoObjectWithUrl(
            @ParameterLayout(named = "Name")
            final String name) {
        final DocDemoObjectWithUrl obj = new DocDemoObjectWithUrl(name, null);
        repositoryService.persist(obj);
        return obj;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

}
