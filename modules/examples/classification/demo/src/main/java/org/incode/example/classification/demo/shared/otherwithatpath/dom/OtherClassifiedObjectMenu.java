package org.incode.example.classification.demo.shared.otherwithatpath.dom;

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
        objectType = "classificationDemoShared.OtherClassifiedObjectMenu"
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.11"
)
public class OtherClassifiedObjectMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<OtherClassifiedObject> listAllOtherObjectsWithAtPath() {
        return repositoryService.allInstances(OtherClassifiedObject.class);
    }


    @MemberOrder(sequence = "2")
    public OtherClassifiedObject createOtherObjectWithAtPath(
            final String name,
            @ParameterLayout(named = "Application tenancy")
            final String atPath) {
        final OtherClassifiedObject obj = new OtherClassifiedObject(name, atPath);
        repositoryService.persist(obj);
        return obj;
    }



    @javax.inject.Inject
    RepositoryService repositoryService;


}
