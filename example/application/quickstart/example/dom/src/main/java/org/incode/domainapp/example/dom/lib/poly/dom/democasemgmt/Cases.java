package org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleLibPoly.Cases"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.1"
)
public class Cases {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Case> listAllCases() {
        return container.allInstances(Case.class);
    }
    //endregion

    //region > create (action)

    @MemberOrder(sequence = "3")
    public Case createCase(final String name) {
        final Case aCase = container.newTransientInstance(Case.class);
        aCase.setName(name);

        container.persistIfNotAlready(aCase);
        return aCase;
    }

    //endregion

    //region > injected services
    @javax.inject.Inject
    DomainObjectContainer container;
    //endregion
}
