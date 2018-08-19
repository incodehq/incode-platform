package org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "fixtureLibPoly.PolyDemoParties"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.4"
)
public class PolyDemoParties {

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<PolyDemoParty> listAllParties() {
        return container.allInstances(PolyDemoParty.class);
    }


    @MemberOrder(sequence = "3")
    public PolyDemoParty createParty(
            final @ParameterLayout(named = "Name") String name) {
        final PolyDemoParty party = container.newTransientInstance(PolyDemoParty.class);
        party.setName(name);

        container.persistIfNotAlready(party);
        return party;
    }


    @javax.inject.Inject 
    DomainObjectContainer container;
}
