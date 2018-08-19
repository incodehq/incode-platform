package org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel;

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
        objectType = "libPolyFixture.PolyDemoCommunicationChannels"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.2"
)
public class PolyDemoCommunicationChannels {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<PolyDemoCommunicationChannel> listAllCommunicationChannels() {
        return container.allInstances(PolyDemoCommunicationChannel.class);
    }


    @javax.inject.Inject 
    DomainObjectContainer container;
}
