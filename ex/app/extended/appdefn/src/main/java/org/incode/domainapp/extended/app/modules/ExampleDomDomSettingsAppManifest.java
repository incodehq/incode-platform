package org.incode.domainapp.extended.app.modules;

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

import org.incode.domainapp.extended.appdefn.DomainAppAppManifestAbstract;
import org.incode.domainapp.extended.appdefn.fixture.DomainAppFixtureScriptsSpecProvider;
import org.incode.domainapp.extended.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.settings.FixturesModuleExamplesSettingsSubmodule;
import org.incode.example.settings.dom.ApplicationSettingsServiceRW;
import org.incode.example.settings.dom.UserSettingsServiceRW;
import org.incode.example.settings.dom.jdo.ApplicationSettingJdo;
import org.incode.example.settings.dom.jdo.UserSettingJdo;
import org.incode.example.settings.fixture.ApplicationSetting_and_UserSetting_create5;

public class ExampleDomDomSettingsAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            FixturesModuleExamplesSettingsSubmodule.class

        )
        .withFixtureScripts(
                ApplicationSetting_and_UserSetting_create5.class,
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
