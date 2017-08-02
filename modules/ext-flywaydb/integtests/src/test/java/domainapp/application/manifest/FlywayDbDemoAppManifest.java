package domainapp.application.manifest;

import org.apache.isis.applib.AppManifestAbstract;

import domainapp.application.fixture.FlywayDemoFixtureSubmodule;
import domainapp.application.services.FlywayDemoServicesSubmodule;
import domainapp.modules.exampledom.ext.flywaydb.ExampleDomExtFlywayDbModule;

/**
 * Bootstrap the application.
 */
public class FlywayDbDemoAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            ExampleDomExtFlywayDbModule.class,
            FlywayDemoFixtureSubmodule.class,
            FlywayDemoServicesSubmodule.class
    );

    public FlywayDbDemoAppManifest() {
        super(BUILDER);
    }

}
