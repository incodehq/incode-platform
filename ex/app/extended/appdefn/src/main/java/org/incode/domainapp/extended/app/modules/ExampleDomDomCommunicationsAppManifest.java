package org.incode.domainapp.extended.app.modules;

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

import org.incode.domainapp.extended.appdefn.DomainAppAppManifestAbstract;
import org.incode.domainapp.extended.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import org.incode.domainapp.extended.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.FixturesModuleExamplesCommunicationsIntegrationSubmodule;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.fixture.DemoObjectWithNotes_and_DemoInvoice_and_docs_and_comms_create;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotes;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotesMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoInvoice;
import org.incode.domainapp.extended.module.fixtures.shared.invoice.dom.DemoInvoiceRepository;
import org.incode.example.communications.dom.CommunicationsModule;
import org.incode.example.country.dom.impl.Country;
import org.incode.example.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.example.document.dom.DocumentModule;

public class ExampleDomDomCommunicationsAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoObjectWithNotes.class,
            DemoInvoice.class,

            FixturesModuleExamplesCommunicationsIntegrationSubmodule.class,
            CommunicationsModule.class,
            DocumentModule.class,
            Country.class,
            FreemarkerDocRenderingModule.class,
            FreeMarkerModule.class
        )
        .withFixtureScripts(
                DemoObjectWithNotes_and_DemoInvoice_and_docs_and_comms_create.class,
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
