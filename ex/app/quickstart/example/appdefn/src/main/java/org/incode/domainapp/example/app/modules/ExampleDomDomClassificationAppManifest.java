package org.incode.domainapp.example.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;

import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPath;
import org.incode.domainapp.example.dom.demo.dom.demowithatpath.DemoObjectWithAtPathMenu;
import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPath;
import org.incode.domainapp.example.dom.demo.dom.otherwithatpath.OtherObjectWithAtPathMenu;
import org.incode.domainapp.example.dom.dom.classification.ExampleDomModuleClassificationModule;
import org.incode.domainapp.example.dom.dom.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3;
import org.incode.module.classification.dom.ClassificationModule;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomClassificationAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoObjectWithAtPath.class,
            OtherObjectWithAtPath.class,

            ExampleDomModuleClassificationModule.class,
            ClassificationModule.class
        )
        .withFixtureScripts(
                DemoObjectWithAtPath_and_OtherObjectWithAtPath_recreate3.class,
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
