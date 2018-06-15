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
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleSpiCommand.SomeCommandAnnotatedObjects",
        repositoryFor = SomeCommandAnnotatedObject.class
)
@DomainServiceLayout(
        named = "SPI Modules",
        menuOrder = "50.2"
)
public class SomeCommandAnnotatedObjects {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SomeCommandAnnotatedObject> listAllSomeCommandAnnotatedObjects() {
        return container.allInstances(SomeCommandAnnotatedObject.class);
    }


    @MemberOrder(sequence = "2")
    public SomeCommandAnnotatedObject createSomeCommandAnnotatedObject(final String name) {
        final SomeCommandAnnotatedObject obj = container.newTransientInstance(SomeCommandAnnotatedObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }


    @javax.inject.Inject 
    DomainObjectContainer container;


}
