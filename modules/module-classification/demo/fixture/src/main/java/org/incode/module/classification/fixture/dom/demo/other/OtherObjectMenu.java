package org.incode.module.classification.fixture.dom.demo.other;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = OtherObject.class
)
@DomainServiceLayout(
        named = "Others",
        menuOrder = "11"
)
public class OtherObjectMenu {


    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<OtherObject> listAll() {
        return repositoryService.allInstances(OtherObject.class);
    }

    //endregion

    //region > createTopLevel (action)
    
    @MemberOrder(sequence = "2")
    public OtherObject create(
            @ParameterLayout(named = "Name")
            final String name,
            @ParameterLayout(named = "Application tenancy")
            final String atPath) {
        final OtherObject obj = new OtherObject(name, atPath);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    //endregion

}
