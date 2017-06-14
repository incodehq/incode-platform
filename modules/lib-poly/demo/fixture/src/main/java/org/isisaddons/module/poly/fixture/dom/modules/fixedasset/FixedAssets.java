package org.isisaddons.module.poly.fixture.dom.modules.fixedasset;

import org.isisaddons.module.poly.fixture.dom.modules.comms.CommunicationChannel;

import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(repositoryFor = CommunicationChannel.class)
@DomainServiceLayout(menuOrder = "10")
public class FixedAssets {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<FixedAsset> listAll() {
        return container.allInstances(FixedAsset.class);
    }
    //endregion


    //region > create (action)

    @MemberOrder(sequence = "3")
    public FixedAsset create(
            final @ParameterLayout(named = "Name") String name) {
        final FixedAsset party = container.newTransientInstance(FixedAsset.class);
        party.setName(name);

        container.persistIfNotAlready(party);
        return party;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion
}
