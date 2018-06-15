package org.incode.domainapp.example.dom.lib.servletapi.dom.demo;

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
        objectType = "exampleLibServletApi.DemoObjects"
)
@DomainServiceLayout(
        named = "Libraries",
        menuOrder = "60.1"
)
public class ServletApiDemoObjects {


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<ServletApiDemoObject> listAllServletApiDemoObjects() {
        return container.allInstances(ServletApiDemoObject.class);
    }



    @MemberOrder(sequence = "2")
    public ServletApiDemoObject createServletApiDemoObject(
            final String name) {
        final ServletApiDemoObject obj = container.newTransientInstance(ServletApiDemoObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }



    @javax.inject.Inject 
    DomainObjectContainer container;

}
