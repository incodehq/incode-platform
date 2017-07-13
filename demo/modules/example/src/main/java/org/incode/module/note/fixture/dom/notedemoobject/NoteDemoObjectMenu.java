package org.incode.module.note.fixture.dom.notedemoobject;

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
        repositoryFor = NoteDemoObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class NoteDemoObjectMenu {


    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<NoteDemoObject> listAll() {
        return container.allInstances(NoteDemoObject.class);
    }

    //endregion

    //region > create (action)
    
    @MemberOrder(sequence = "2")
    public NoteDemoObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final NoteDemoObject obj = container.newTransientInstance(NoteDemoObject.class);
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
