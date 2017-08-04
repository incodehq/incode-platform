package domainapp.appdefn.services.homepage.flywaydb;

import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectRepository;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "domainapp.appdefn.services.homepage.flywaydb.HomePageViewModel"
)
public class HomePageViewModel {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{num} objects", "num", getObjects().size());
    }
    //endregion

    //region > object (collection)
//    @HomePage
    public List<DemoObject> getObjects() {
        return flywayDemoObjectRepository.listAll();
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    DemoObjectRepository flywayDemoObjectRepository;

    //endregion
}
