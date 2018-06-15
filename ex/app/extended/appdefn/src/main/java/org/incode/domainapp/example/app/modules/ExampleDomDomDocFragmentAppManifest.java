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

import org.incode.domainapp.module.fixtures.per_cpt.examples.docfragment.ExampleDomModuleDocFragmentModule;
import org.incode.domainapp.module.fixtures.per_cpt.examples.docfragment.fixture.DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_recreate;
import org.incode.domainapp.module.fixtures.shared.customer.dom.DemoCustomer;
import org.incode.domainapp.module.fixtures.shared.customer.dom.DemoCustomerMenu;
import org.incode.domainapp.module.fixtures.shared.invoicewithatpath.dom.DemoInvoiceWithAtPath;
import org.incode.domainapp.module.fixtures.shared.invoicewithatpath.dom.DemoInvoiceWithAtPathMenu;
import org.incode.example.docfragment.dom.DocFragmentModuleDomModule;
import org.incode.example.docfragment.dom.impl.DocFragment;
import org.incode.example.docfragment.dom.impl.DocFragmentRepository;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomDocFragmentAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoCustomer.class,
            DemoInvoiceWithAtPath.class,

            ExampleDomModuleDocFragmentModule.class,
            DocFragmentModuleDomModule.class

        )
        .withFixtureScripts(
                DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_recreate.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                DomainAppFixtureScriptsSpecProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomDomDocFragmentAppManifest() {
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
        public List<DemoCustomer> getDemoCustomers() {
            return demoCustomerMenu.listAllDemoCustomers();
        }

        @CollectionLayout(defaultView = "table")
        public List<DemoInvoiceWithAtPath> getDemoInvoicesWithAtPath() {
            return demoInvoiceWithAtPathMenu.listAllDemoInvoices();
        }

        @CollectionLayout(defaultView = "table")
        public List<DocFragment> getDocFragments() {
            return docFragmentRepository.listAll();
        }

        @Inject DemoCustomerMenu demoCustomerMenu;

        @Inject DemoInvoiceWithAtPathMenu demoInvoiceWithAtPathMenu;

        @Inject DocFragmentRepository docFragmentRepository;

    }

}
