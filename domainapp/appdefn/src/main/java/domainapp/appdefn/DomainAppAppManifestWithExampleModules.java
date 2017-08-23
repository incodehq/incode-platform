package domainapp.appdefn;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.example.app.ExampleAppSubmodule;
import org.incode.domainapp.example.dom.ExampleDomSubmodule;

public class DomainAppAppManifestWithExampleModules extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifest.BUILDER
            .withAdditionalModules(
                    ExampleAppSubmodule.class,
                    ExampleDomSubmodule.class
            );

    public DomainAppAppManifestWithExampleModules() {
        super(BUILDER);
    }

}
