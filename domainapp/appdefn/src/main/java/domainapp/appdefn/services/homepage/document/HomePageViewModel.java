package domainapp.appdefn.services.homepage.document;

import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.incode.domainapp.example.dom.dom.document.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.dom.document.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.document.dom.demo2.OtherObject;
import org.incode.domainapp.example.dom.dom.document.dom.demo2.OtherObjectMenu;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "domainapp.HomePageViewModel"
)
public class HomePageViewModel {

    public TranslatableString title() {
        return TranslatableString.tr("{demo} demo, {other} other", "demo", getDemoObjects().size(), "other", getOtherObjects().size());
    }

    public List<DemoObject> getDemoObjects() {
        return demoObjectMenu.listAll();
    }

    public List<OtherObject> getOtherObjects() {
        return otherObjectMenu.listAll();
    }

    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;

    @javax.inject.Inject
    OtherObjectMenu otherObjectMenu;

}
