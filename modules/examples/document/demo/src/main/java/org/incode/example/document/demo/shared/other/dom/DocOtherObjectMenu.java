package org.incode.example.document.demo.shared.other.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDocumentDemo.DocOtherObjectMenu"
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.10"
)
public class DocOtherObjectMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DocOtherObject> listAllOtherObjects() {
        return repositoryService.allInstances(DocOtherObject.class);
    }



    @MemberOrder(sequence = "2")
    public DocOtherObject createOtherObjects(final String name) {
        final DocOtherObject obj = new DocOtherObject(name);
        repositoryService.persist(obj);
        return obj;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

}
