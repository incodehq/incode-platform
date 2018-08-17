package org.incode.domainapp.extended.appdefn.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs.dom.DemoObjectWithBlob;

@DomainService(
        objectType = "org.incode.domainapp.example.dom.app.HomePageService",
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY // trick to suppress the actions from the top-level menu
)
public class HomePageService {

    @Action(semantics = SemanticsOf.SAFE)
    @HomePage
    public HomePageViewModel homePage() {
        HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);

        final List<DemoObjectWithBlob> demoObjects = viewModel.getWktPdfjsDemoObjectsWithBlob();
        if (!demoObjects.isEmpty()) {
            viewModel.setIdx(0);
        }

        return viewModel;
    }


    @javax.inject.Inject
    FactoryService factoryService;
}
