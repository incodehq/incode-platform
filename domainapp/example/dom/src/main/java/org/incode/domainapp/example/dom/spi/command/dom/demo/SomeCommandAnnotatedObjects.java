package org.incode.domainapp.example.dom.spi.command.dom.demo;

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
        objectType = "exampleSpiCommand.SomeCommandAnnotatedObjects",
        repositoryFor = SomeCommandAnnotatedObject.class
)
@DomainServiceLayout(
        menuOrder = "10",
        named = "Command annotated objects"
)
public class SomeCommandAnnotatedObjects {


    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<SomeCommandAnnotatedObject> listAll() {
        return container.allInstances(SomeCommandAnnotatedObject.class);
    }

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public SomeCommandAnnotatedObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final SomeCommandAnnotatedObject obj = container.newTransientInstance(SomeCommandAnnotatedObject.class);
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
