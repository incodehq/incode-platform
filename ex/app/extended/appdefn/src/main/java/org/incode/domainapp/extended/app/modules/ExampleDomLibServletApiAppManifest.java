package org.incode.domainapp.extended.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.module.servletapi.ServletApiModule;

import org.incode.domainapp.extended.appdefn.DomainAppAppManifestAbstract;
import org.incode.domainapp.extended.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.FixturesModuleLibServletApiSubmodule;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.dom.demo.ServletApiDemoObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.dom.demo.ServletApiDemoObjects;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.fixture.ServletApiDemoObject_create3;

public class ExampleDomLibServletApiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            FixturesModuleLibServletApiSubmodule.class,
            ServletApiModule.class
        )
        .withFixtureScripts(
                ServletApiDemoObject_create3.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomLibServletApiAppManifest() {
        super(BUILDER);
    }

    @DomainObject(
            objectType = "HomePageProvider" // bit of a hack; this is a (manually registered) service actually
    )
    public static class HomePageProvider {

        @HomePage
        public HomePageViewModel homePage() {
            return new HomePageViewModel();
        }
    }

    @DomainObject(
            nature = Nature.VIEW_MODEL,
            objectType = "HomePageViewModel"
    )
    public static class HomePageViewModel {

        public String title() { return "Home page"; }

        @CollectionLayout(defaultView = "table")
        public List<ServletApiDemoObject> getServletApiDemoObjects() {
            return servletApiDemoObjects.listAllServletApiDemoObjects();
        }

        @Inject
        ServletApiDemoObjects servletApiDemoObjects;

    }

}
