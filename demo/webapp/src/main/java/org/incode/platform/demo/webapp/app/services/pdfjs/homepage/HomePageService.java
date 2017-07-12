package org.incode.platform.demo.webapp.app.services.pdfjs.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;

import org.isisaddons.wicket.pdfjs.fixture.dom.demo.DemoObject;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class HomePageService {

    @Action(semantics = SemanticsOf.SAFE)
    @HomePage
    public HomePageViewModel homePage() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        final List<DemoObject> demoObjects = viewModel.getDemoObjects();
        if (!demoObjects.isEmpty()) {
            viewModel.setIdx(0);
        }

        return viewModel;
    }


    @javax.inject.Inject
    FactoryService factoryService;
}
