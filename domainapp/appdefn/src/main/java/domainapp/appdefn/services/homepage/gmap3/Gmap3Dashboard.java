package domainapp.appdefn.services.homepage.gmap3;

import java.util.List;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.RenderType;

import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "domainapp.appdefn.services.homepage.gmap3.Gmap3Dashboard"
)
@DomainObjectLayout(
        named = "Dashboard"
)
@MemberGroupLayout(columnSpans = { 0, 0, 0, 12 })
public class Gmap3Dashboard extends AbstractViewModel {



    @Override
    public String viewModelMemento() {
        return "dashboard";
    }

    @Override
    public void viewModelInit(String memento) {
        // nothing to do
    }


    @MemberOrder(sequence = "1")
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<DemoToDoItem> getNotYetComplete() {
        return demoToDoItemMenu.notYetCompleteNoUi();
    }


    @MemberOrder(sequence = "2")
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<DemoToDoItem> getComplete() {
        return demoToDoItemMenu.completeNoUi();
    }


    @javax.inject.Inject
    private DemoToDoItemMenu demoToDoItemMenu;

}