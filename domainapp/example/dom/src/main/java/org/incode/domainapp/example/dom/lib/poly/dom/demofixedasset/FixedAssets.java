package org.incode.domainapp.example.dom.lib.poly.dom.demofixedasset;

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

/**
 * As used by lib-poly
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleLibPoly.FixedAssets"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.3"
)
public class FixedAssets {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<FixedAsset> listAllFixedAssets() {
        return container.allInstances(FixedAsset.class);
    }



    @MemberOrder(sequence = "3")
    public FixedAsset createFixedAsset(final String name) {
        final FixedAsset party = container.newTransientInstance(FixedAsset.class);
        party.setName(name);
        container.persistIfNotAlready(party);
        return party;
    }

    @javax.inject.Inject
    DomainObjectContainer container;

}
