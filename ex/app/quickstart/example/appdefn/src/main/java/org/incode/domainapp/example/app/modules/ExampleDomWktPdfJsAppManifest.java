package org.incode.domainapp.example.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.wicket.pdfjs.cpt.PdfjsCptModule;

import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlob;
import org.incode.domainapp.example.dom.demo.dom.demowithblob.DemoObjectWithBlobMenu;
import org.incode.domainapp.example.dom.demo.fixture.setup.DemoObjectWithBlob_createUpTo5_fakeData;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomWktPdfJsAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoObjectWithBlob.class,

            PdfjsCptModule.class,

            FakeDataModule.class // used by the fixture (below)
        )
        .withFixtureScripts(
                DemoObjectWithBlob_createUpTo5_fakeData.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomWktPdfJsAppManifest() {
        super(BUILDER);
    }

    @DomainObject(
            objectType = "HomePageProvider" // bit of a hack; this is a (manually registered) service actually
    )
    public static class HomePageProvider {

        @HomePage
        public List<DemoObjectWithBlob> homePage() {
            return menu.listAllDemoObjectsWithBlob();
        }

        @Inject
        DemoObjectWithBlobMenu menu;
    }

}
