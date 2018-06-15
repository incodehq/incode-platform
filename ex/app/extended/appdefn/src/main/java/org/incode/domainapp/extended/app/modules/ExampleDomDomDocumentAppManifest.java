package org.incode.domainapp.extended.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;
import org.isisaddons.module.xdocreport.dom.XDocReportModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.ExampleDomModuleDocumentModule;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.fixture.DemoObjectWithUrl_and_OtherObject_and_docrefdata_recreate;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrlMenu;
import org.incode.domainapp.extended.module.fixtures.shared.other.dom.OtherObject;
import org.incode.domainapp.extended.module.fixtures.shared.other.dom.OtherObjectMenu;
import org.incode.example.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.example.docrendering.stringinterpolator.dom.StringInterpolatorDocRenderingModule;
import org.incode.example.docrendering.xdocreport.dom.XDocReportDocRenderingModule;
import org.incode.example.document.dom.DocumentModule;

import org.incode.domainapp.extended.appdefn.DomainAppAppManifestAbstract;
import org.incode.domainapp.extended.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import org.incode.domainapp.extended.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomDocumentAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoObjectWithUrl.class,
            OtherObject.class,

            ExampleDomModuleDocumentModule.class,
            DocumentModule.class,

            FreemarkerDocRenderingModule.class,
            FreeMarkerModule.class,

            StringInterpolatorDocRenderingModule.class,
            StringInterpolatorModule.class,

            XDocReportDocRenderingModule.class,
            XDocReportModule.class,

            FakeDataModule.class
        )
        .withFixtureScripts(
                DemoObjectWithUrl_and_OtherObject_and_docrefdata_recreate.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                DomainAppFixtureScriptsSpecProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomDomDocumentAppManifest() {
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
        public List<DemoObjectWithUrl> getDemoObjectsWithUrl() {
            return demoObjectWithUrlMenu.listAllDemoObjectsWithUrl();
        }

        @CollectionLayout(defaultView = "table")
        public List<OtherObject> getOtherObjects() {
            return otherObjectMenu.listAllOtherObjects();
        }

        @Inject
        DemoObjectWithUrlMenu demoObjectWithUrlMenu;

        @Inject
        OtherObjectMenu otherObjectMenu;

    }

}
