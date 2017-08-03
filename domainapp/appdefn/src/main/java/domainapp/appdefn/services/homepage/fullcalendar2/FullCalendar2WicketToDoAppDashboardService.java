package domainapp.appdefn.services.homepage.fullcalendar2;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        objectType = "domainapp.appdefn.services.homepage.fullcalendar2.FullCalendar2WicketToDoAppDashboardService",
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY // trick
)
public class FullCalendar2WicketToDoAppDashboardService {

    //region > identification in the UI

    private static final String ID = "dashboard";

    //endregion

    //region > homePage

    @Action(
            semantics = SemanticsOf.SAFE
    )
//    @HomePage
    public FullCalendar2WicketToDoAppDashboard lookup() {
        return container.newViewModelInstance(FullCalendar2WicketToDoAppDashboard.class, ID);
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    //endregion
}
