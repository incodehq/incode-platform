package domainapp.appdefn.services.homepage.fullcalendar2;

import java.util.List;
import org.incode.domainapp.example.dom.wkt.fullcalendar2.dom.demo.FullCalendar2WicketToDoItem;
import org.incode.domainapp.example.dom.wkt.fullcalendar2.dom.demo.FullCalendar2WicketToDoItems;
import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.RenderType;

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named = "Dashboard"
)
@MemberGroupLayout(columnSpans={0,0,0,12})
public class FullCalendar2WicketToDoAppDashboard extends AbstractViewModel {

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

    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<FullCalendar2WicketToDoItem> getNotYetComplete() {
        return toDoItems.notYetCompleteNoUi();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private FullCalendar2WicketToDoItems toDoItems;
    //endregion

}
