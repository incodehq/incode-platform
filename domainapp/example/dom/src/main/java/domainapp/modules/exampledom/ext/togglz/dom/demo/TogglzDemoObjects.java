package domainapp.modules.exampledom.ext.togglz.dom.demo;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import domainapp.modules.exampledom.ext.togglz.dom.featuretoggle.TogglzDemoFeature;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isistogglzDemo.TogglzDemoObjects",
        repositoryFor = TogglzDemoObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class TogglzDemoObjects {

    //region > listAll (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<TogglzDemoObject> listAll() {
        return container.allInstances(TogglzDemoObject.class);
    }
    public boolean hideListAll() {
        return !TogglzDemoFeature.listAll.isActive();
    }

    //endregion

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<TogglzDemoObject> findByName(final String name) {
        return Lists.newArrayList(
                Iterables.filter(listAll(), o -> o.getName() != null && o.getName().contains(name))
        );
    }
    public boolean hideFindByName() {
        return !TogglzDemoFeature.findByName.isActive();
    }

    //endregion

    //region > create (action)
    
    @MemberOrder(sequence = "3")
    public TogglzDemoObject create(final String name) {
        final TogglzDemoObject obj = container.newTransientInstance(TogglzDemoObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }
    public boolean hideCreate() {
        return !TogglzDemoFeature.create.isActive();
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
