package domainapp.appdefn.services.homepage.document;

import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.domainapp.example.dom.demo.dom.demowithurl.DemoObjectWithUrl;
import org.incode.domainapp.example.dom.demo.dom.demowithurl.DemoObjectWithUrlMenu;
import org.incode.domainapp.example.dom.demo.dom.other.OtherObject;
import org.incode.domainapp.example.dom.demo.dom.other.OtherObjectMenu;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "domainapp.appdefn.services.homepage.document.HomePageViewModel"
)
public class HomePageViewModel {

    public TranslatableString title() {
        return TranslatableString.tr("{demo} demo, {other} other", "demo", getDemoObjects().size(), "other", getOtherObjects().size());
    }

    public List<DemoObjectWithUrl> getDemoObjects() {
        return demoObjectMenu.listAll();
    }

    public List<OtherObject> getOtherObjects() {
        return otherObjectMenu.listAll();
    }

    @javax.inject.Inject
    DemoObjectWithUrlMenu demoObjectMenu;

    @javax.inject.Inject
    OtherObjectMenu otherObjectMenu;

}
