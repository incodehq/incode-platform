package org.incode.domainapp.example.dom.lib.poly.dom.demoparty;

import org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannel;

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
@DomainServiceLayout(menuOrder = "20")
public class Parties {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Party> listAll() {
        return container.allInstances(Party.class);
    }
    //endregion


    //region > create (action)

    @MemberOrder(sequence = "3")
    public Party create(
            final @ParameterLayout(named = "Name") String name) {
        final Party party = container.newTransientInstance(Party.class);
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
