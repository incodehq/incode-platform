package org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt;

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
        objectType = "libPolyFixture.PolyDemoCases"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.1"
)
public class PolyDemoCases {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<PolyDemoCase> listAllCases() {
        return container.allInstances(PolyDemoCase.class);
    }
    //endregion

    //region > create (action)

    @MemberOrder(sequence = "3")
    public PolyDemoCase createCase(final String name) {
        final PolyDemoCase aCase = container.newTransientInstance(PolyDemoCase.class);
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
