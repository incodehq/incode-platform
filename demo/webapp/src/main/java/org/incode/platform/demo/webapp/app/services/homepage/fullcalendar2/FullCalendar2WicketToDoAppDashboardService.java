package org.incode.platform.demo.webapp.app.services.homepage.fullcalendar2;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY // trick
)
public class FullCalendar2WicketToDoAppDashboardService {

    //region > identification in the UI

    private static final String ID = "dashboard";

    public String getId() {
        return ID;
    }

    public String iconName() {
        return ID;
    }

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
