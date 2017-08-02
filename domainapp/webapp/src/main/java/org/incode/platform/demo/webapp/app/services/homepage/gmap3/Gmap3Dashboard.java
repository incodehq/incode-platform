package org.incode.platform.demo.webapp.app.services.homepage.gmap3;

import java.util.List;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.RenderType;

import org.incode.domainapp.example.dom.wkt.gmap3.dom.demo.Gmap3ToDoItem;
import org.incode.domainapp.example.dom.wkt.gmap3.dom.demo.Gmap3WicketToDoItems;

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named = "Dashboard"
)
@MemberGroupLayout(columnSpans = { 0, 0, 0, 12 })
public class Gmap3Dashboard extends AbstractViewModel {

    //region > title, iconName

    public String title() {
        return "Dashboard";
    }

    public String iconName() {
        return "Dashboard";
    }
    //endregion

    //region > Viewmodel contract

    @Override
    public String viewModelMemento() {
        return "dashboard";
    }

    @Override
    public void viewModelInit(String memento) {
        // nothing to do
    }
    //endregion

    //region > notYetComplete (derived collection)

    @MemberOrder(sequence = "1")
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<Gmap3ToDoItem> getNotYetComplete() {
        return gmap3WicketToDoItems.notYetCompleteNoUi();
    }
    //endregion

    //region > complete (derived collection)

    @MemberOrder(sequence = "2")
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<Gmap3ToDoItem> getComplete() {
        return gmap3WicketToDoItems.completeNoUi();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private Gmap3WicketToDoItems gmap3WicketToDoItems;
    //endregion

}