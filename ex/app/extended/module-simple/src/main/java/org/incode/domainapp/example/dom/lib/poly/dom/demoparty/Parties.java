package org.incode.domainapp.example.dom.lib.poly.dom.demoparty;

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
        objectType = "exampleLibPoly.Parties"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.4"
)
public class Parties {

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Party> listAllParties() {
        return container.allInstances(Party.class);
    }


    @MemberOrder(sequence = "3")
    public Party createParty(
            final @ParameterLayout(named = "Name") String name) {
        final Party party = container.newTransientInstance(Party.class);
        party.setName(name);

        container.persistIfNotAlready(party);
        return party;
    }


    @javax.inject.Inject 
    DomainObjectContainer container;
}
