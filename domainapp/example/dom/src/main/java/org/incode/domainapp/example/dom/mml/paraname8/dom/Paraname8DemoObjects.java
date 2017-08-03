package org.incode.domainapp.example.dom.mml.paraname8.dom;

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
        objectType = "exampleMmlParaname8.Paraname8DemoObjects"
)
@DomainServiceLayout(
        menuOrder = "10",
        named = "Paraname8 Demo Objects"
)
public class Paraname8DemoObjects {

    //region > identification in the UI

    public String getId() {
        return "paraname8Demo";
    }

    public String iconName() {
        return "Paramane8DemoObject";
    }

    //endregion

    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Paraname8DemoObject> listAll() {
        return container.allInstances(Paraname8DemoObject.class);
    }

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public Paraname8DemoObject create(
            final /* @ParameterLayout(named="Name") */ String name) {
        final Paraname8DemoObject obj = container.newTransientInstance(Paraname8DemoObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        container.flush();
        return obj;
    }

    @MemberOrder(sequence = "4")
    public Paraname8DemoObject createWithNamedOverride(
            final @ParameterLayout(named="Named Override") String name) {
        final Paraname8DemoObject obj = container.newTransientInstance(Paraname8DemoObject.class);
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
