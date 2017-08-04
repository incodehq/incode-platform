package domainapp.appdefn.services.homepage.pdfjs;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlob;

@DomainService(
        objectType = "domainapp.appdefn.services.homepage.pdfjs.HomePageService",
        nature = NatureOfService.DOMAIN
)
public class HomePageService {

    @Action(semantics = SemanticsOf.SAFE)
//    @HomePage
    public HomePageViewModel homePage() {
        final HomePageViewModel viewModel = factoryService.instantiate(HomePageViewModel.class);
        final List<DemoObjectWithBlob> demoObjects = viewModel.getDemoObjects();
        if (!demoObjects.isEmpty()) {
            viewModel.setIdx(0);
        }

        return viewModel;
    }


    @javax.inject.Inject
    FactoryService factoryService;
}
