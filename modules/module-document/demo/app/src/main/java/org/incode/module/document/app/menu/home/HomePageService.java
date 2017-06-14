package org.incode.module.document.app.menu.home;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;

@DomainService(
        nature = NatureOfService.DOMAIN // trick to suppress the actions from the top-level menu
)
public class HomePageService {

    @Action(semantics = SemanticsOf.SAFE)
    @HomePage
    public HomePageViewModel homePage() {
        return factoryService.instantiate(HomePageViewModel.class);
    }


    @javax.inject.Inject
    FactoryService factoryService;
}
