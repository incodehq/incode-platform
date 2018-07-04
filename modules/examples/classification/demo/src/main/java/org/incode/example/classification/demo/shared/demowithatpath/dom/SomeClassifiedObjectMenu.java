package org.incode.example.classification.demo.shared.demowithatpath.dom;

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
        objectType = "classificationDemoShared.SomeClassifiedObjectMenu"
)
@DomainServiceLayout(
        named = "Demo",
        menuOrder = "10.3"
)
public class SomeClassifiedObjectMenu {


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<SomeClassifiedObject> listAllOfSomeClassifiedObjects() {
        return repositoryService.allInstances(SomeClassifiedObject.class);
    }


    @MemberOrder(sequence = "2")
    public SomeClassifiedObject createSomeClassifiedObject(
            @ParameterLayout(named = "Name")
            final String name,
            @ParameterLayout(named = "Application tenancy")
            final String atPath) {
        final SomeClassifiedObject obj = new SomeClassifiedObject(name, atPath);
        repositoryService.persist(obj);
        return obj;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
