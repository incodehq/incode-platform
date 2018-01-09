package org.incode.domainapp.example.dom.lib.poly.dom.democommchannel;

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
        objectType = "exampleLibPoly.CommunicationChannels"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "40.3.2"
)
public class CommunicationChannels {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<CommunicationChannel> listAllCommunicationChannels() {
        return container.allInstances(CommunicationChannel.class);
    }


    @javax.inject.Inject 
    DomainObjectContainer container;
}
