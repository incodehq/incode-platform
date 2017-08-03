package domainapp.appdefn.services.homepage.poly;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import domainapp.appdefn.services.homepage.poly.HomePage;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY // trick to suppress the actions from the top-level menu
)
public class HomePageService {

    //region > homePage (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @org.apache.isis.applib.annotation.HomePage
    public HomePage homePage() {
        return container.injectServicesInto(new HomePage());
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    //endregion
}
