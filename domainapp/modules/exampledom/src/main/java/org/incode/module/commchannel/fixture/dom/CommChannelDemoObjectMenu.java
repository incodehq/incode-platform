package org.incode.module.commchannel.fixture.dom;

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
        nature = NatureOfService.VIEW,
        repositoryFor = CommChannelDemoObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class CommChannelDemoObjectMenu {


    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<CommChannelDemoObject> listAll() {
        return container.allInstances(CommChannelDemoObject.class);
    }

    //endregion

    //region > create (action)
    
    @MemberOrder(sequence = "2")
    public CommChannelDemoObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final CommChannelDemoObject obj = container.newTransientInstance(CommChannelDemoObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
