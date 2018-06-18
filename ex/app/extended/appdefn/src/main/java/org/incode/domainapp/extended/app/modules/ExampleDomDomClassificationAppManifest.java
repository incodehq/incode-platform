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

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.FixturesModuleExamplesClassificationIntegrationSubmodule;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3;
import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPath;
import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.dom.DemoObjectWithAtPathMenu;
import org.incode.domainapp.extended.module.fixtures.shared.otherwithatpath.dom.OtherObjectWithAtPath;
import org.incode.domainapp.extended.module.fixtures.shared.otherwithatpath.dom.OtherObjectWithAtPathMenu;
import org.incode.example.classification.ClassificationModule;

import org.incode.domainapp.extended.appdefn.DomainAppAppManifestAbstract;
import org.incode.domainapp.extended.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import org.incode.domainapp.extended.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomClassificationAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoObjectWithAtPath.class,
            OtherObjectWithAtPath.class,

            FixturesModuleExamplesClassificationIntegrationSubmodule.class,
            ClassificationModule.class
        )
        .withFixtureScripts(
                DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                DomainAppFixtureScriptsSpecProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomDomClassificationAppManifest() {
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
        public List<DemoObjectWithAtPath> getDemoObjectsWithAtPath() {
            return demoObjectWithAtPathMenu.listAllDemoObjectsWithAtPath();
        }

        @CollectionLayout(defaultView = "table")
        public List<OtherObjectWithAtPath> getOtherObjectsWithAtPath() {
            return otherObjectWithAtPathMenu.listAllOtherObjectsWithAtPath();
        }

        @Inject
        DemoObjectWithAtPathMenu demoObjectWithAtPathMenu;

        @Inject
        OtherObjectWithAtPathMenu otherObjectWithAtPathMenu;

    }

}
