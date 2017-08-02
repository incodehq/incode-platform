package org.incode.domainapp.example.dom.dom.classification.dom.demo;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = DemoObject.class
)
@DomainServiceLayout(
        named = "Some",
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

    //region > createTopLevel (action)
    
    @MemberOrder(sequence = "2")
    public DemoObject create(
            @ParameterLayout(named = "Name")
            final String name,
            @ParameterLayout(named = "Application tenancy")
            final String atPath) {
        final DemoObject obj = new DemoObject(name, atPath);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    //endregion

}
