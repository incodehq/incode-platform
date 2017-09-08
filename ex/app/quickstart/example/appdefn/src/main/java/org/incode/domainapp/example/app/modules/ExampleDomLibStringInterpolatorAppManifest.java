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
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;

import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminder;
import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminderMenu;
import org.incode.domainapp.example.dom.demo.fixture.reminders.DemoReminder_recreate4;
import org.incode.domainapp.example.dom.lib.stringinterpolator.ExampleDomLibStringInterpolatorModule;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomLibStringInterpolatorAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            DemoReminder.class,
            ExampleDomLibStringInterpolatorModule.class,
            StringInterpolatorModule.class
        )
        .withFixtureScripts(
                DemoReminder_recreate4.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        )
        .withConfigurationProperty("isis.website", "http://isis.apache.org");

    public ExampleDomLibStringInterpolatorAppManifest() {
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
        public List<DemoReminder> getDemoReminders() {
            return demoReminderMenu.listAllReminders();
        }

        @Inject
        DemoReminderMenu demoReminderMenu;

    }

}
