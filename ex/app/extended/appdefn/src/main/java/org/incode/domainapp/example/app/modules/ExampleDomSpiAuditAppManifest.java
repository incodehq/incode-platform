package org.incode.domainapp.example.app.modules;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.audit.AuditModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;

import org.incode.domainapp.example.dom.spi.audit.ExampleDomSpiAuditModule;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.audited.SomeAuditedObject;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.audited.SomeAuditedObjects;
import org.incode.domainapp.example.dom.spi.audit.fixture.SomeAuditedObject_and_SomeNonAuditedObject_recreate3;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomSpiAuditAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            ExampleDomSpiAuditModule.class,
            AuditModule.class
        )
        .withFixtureScripts(
                SomeAuditedObject_and_SomeNonAuditedObject_recreate3.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomSpiAuditAppManifest() {
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
        public List<SomeAuditedObject> getAuditedObjects() {
            return someAuditedObjects.listAllSomeAuditedObjects();
        }

        @Inject
        SomeAuditedObjects someAuditedObjects;

    }

}
