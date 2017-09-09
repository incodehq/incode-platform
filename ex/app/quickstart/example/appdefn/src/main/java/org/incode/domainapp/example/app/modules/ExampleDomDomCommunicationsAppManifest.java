package org.incode.domainapp.example.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;

import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotesMenu;
import org.incode.domainapp.example.dom.demo.dom.invoice.DemoInvoice;
import org.incode.domainapp.example.dom.demo.dom.invoice.DemoInvoiceRepository;
import org.incode.domainapp.example.dom.dom.communications.ExampleDomModuleCommunicationsModule;
import org.incode.domainapp.example.dom.dom.communications.fixture.DemoObjectWithNotes_and_DemoInvoice_and_docs_and_comms_recreate;
import org.incode.module.communications.dom.CommunicationsModule;
import org.incode.module.country.dom.impl.Country;
import org.incode.module.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.module.document.dom.DocumentModule;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomCommunicationsAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoObjectWithNotes.class,
            DemoInvoice.class,

            ExampleDomModuleCommunicationsModule.class,
            CommunicationsModule.class,
            DocumentModule.class,
            Country.class,
            FreemarkerDocRenderingModule.class,
            FreeMarkerModule.class
        )
        .withFixtureScripts(
                DemoObjectWithNotes_and_DemoInvoice_and_docs_and_comms_recreate.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                DomainAppFixtureScriptsSpecProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomDomCommunicationsAppManifest() {
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
        public List<DemoObjectWithNotes> getDemoObjectsWithNotes() {
            return demoObjectWithNotesMenu.listAllDemoObjectsWithNotes();
        }

        @CollectionLayout(defaultView = "table")
        public List<DemoInvoice> getDemoInvoices() {
            return demoInvoiceRepository.listAll();
        }

        @Inject DemoObjectWithNotesMenu demoObjectWithNotesMenu;

        @Inject DemoInvoiceRepository demoInvoiceRepository;

    }

}
