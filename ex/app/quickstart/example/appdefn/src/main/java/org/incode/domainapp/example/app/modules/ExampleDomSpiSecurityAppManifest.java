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

import org.incode.domainapp.example.dom.spi.security.ExampleDomSpiSecurityModule;
import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntity;
import org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted.TenantedEntities;
import org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted.TenantedEntity;
import org.incode.domainapp.example.dom.spi.security.fixture.SecurityModuleAppSetUp;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomSpiSecurityAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            ExampleDomSpiSecurityModule.class
        )
        .withFixtureScripts(
                SecurityModuleAppSetUp.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomSpiSecurityAppManifest() {
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
        public List<NonTenantedEntity> getNonTenantedEntities() {
            return nonTenantedEntities.listAllNonTenantedEntities();
        }

        @CollectionLayout(defaultView = "table")
        public List<TenantedEntity> getTenantedEntities() {
            return tenantedEntities.listAllTenantedEntities();
        }

        @Inject
        NonTenantedEntities nonTenantedEntities;
        @Inject
        TenantedEntities tenantedEntities;

    }

}
