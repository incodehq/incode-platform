package org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset;

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
        objectType = "libPolyFixture.PolyDemoFixedAssets"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.3"
)
public class PolyDemoFixedAssets {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<PolyDemoFixedAsset> listAllFixedAssets() {
        return container.allInstances(PolyDemoFixedAsset.class);
    }



    @MemberOrder(sequence = "3")
    public PolyDemoFixedAsset createFixedAsset(final String name) {
        final PolyDemoFixedAsset party = container.newTransientInstance(PolyDemoFixedAsset.class);
        party.setName(name);
        container.persistIfNotAlready(party);
        return party;
    }

    @javax.inject.Inject
    DomainObjectContainer container;

}
