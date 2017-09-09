package org.incode.domainapp.example.app.modules;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo;
import org.isisaddons.module.settings.dom.jdo.UserSettingJdo;

import org.incode.domainapp.example.dom.dom.settings.ExampleDomModuleSettingsModule;
import org.incode.domainapp.example.dom.dom.settings.fixture.ApplicationSetting_and_UserSetting_recreate5;

import domainapp.appdefn.DomainAppAppManifestAbstract;
import domainapp.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class ExampleDomDomSettingsAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            ExampleDomModuleSettingsModule.class

        )
        .withFixtureScripts(
                ApplicationSetting_and_UserSetting_recreate5.class,
                SeedSuperAdministratorRoleAndSvenSuperUser.class
        )
        .withAdditionalServices(
                HomePageProvider.class,
                DomainAppFixtureScriptsSpecProvider.class,
                // necessary because of ISIS-1710
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class
        );

    public ExampleDomDomSettingsAppManifest() {
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
        public List<ApplicationSettingJdo> getApplicationSettings() {
            return applicationSettingsServiceRW.listAll()
                    .stream()
                    .filter(ApplicationSettingJdo.class::isInstance)
                    .map(ApplicationSettingJdo.class::cast)
                    .collect(Collectors.toList());
        }

        @CollectionLayout(defaultView = "table")
        public List<UserSettingJdo> getUserSettings() {
            return userSettingsServiceRW.listAll()
                    .stream()
                    .filter(UserSettingJdo.class::isInstance)
                    .map(UserSettingJdo.class::cast)
                    .collect(Collectors.toList());
        }

        @Inject
        ApplicationSettingsServiceRW applicationSettingsServiceRW;

        @Inject
        UserSettingsServiceRW userSettingsServiceRW;

    }

}
