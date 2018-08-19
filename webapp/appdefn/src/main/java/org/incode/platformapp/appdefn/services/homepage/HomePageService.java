package org.incode.platformapp.appdefn.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;

import org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.dom.PdfJsDemoObjectWithBlob;

@DomainService(
        objectType = "platformapp.HomePageService",
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY // trick to suppress the actions from the top-level menu
)
public class HomePageService {

    @Action(semantics = SemanticsOf.SAFE)
    @HomePage
    public HomePageViewModel homePage() {
        HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);

        final List<PdfJsDemoObjectWithBlob> demoObjects = viewModel.getWktPdfjsDemoObjectsWithBlob();
        if (!demoObjects.isEmpty()) {
            viewModel.setIdx(0);
        }

        return viewModel;
    }


    @javax.inject.Inject
    FactoryService factoryService;
}
