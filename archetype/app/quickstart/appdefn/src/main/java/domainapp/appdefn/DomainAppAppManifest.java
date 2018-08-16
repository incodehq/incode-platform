package domainapp.appdefn;

import java.util.List;

import org.apache.isis.applib.AppManifestAbstract2;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.audit.AuditModule;
import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;
import org.isisaddons.module.togglz.TogglzModule;
import org.isisaddons.wicket.excel.cpt.ui.ExcelUiModule;
import org.isisaddons.wicket.fullcalendar2.cpt.ui.FullCalendar2UiModule;
import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.service.Gmap3ServiceModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;
import org.isisaddons.wicket.pdfjs.cpt.PdfjsCptModule;
import org.isisaddons.wicket.summernote.cpt.ui.SummernoteUiModule;
import org.isisaddons.wicket.wickedcharts.cpt.ui.WickedChartsUiModule;

import org.incode.example.settings.SettingsModule;
import org.incode.module.base.services.BaseServicesModule;

import domainapp.appdefn.seed.security.SeedSuperAdministratorRoleAndSvenSuperUser;

public class DomainAppAppManifest extends AppManifestAbstract2 {

    public static final AppManifestAbstract2.Builder BUILDER =
            Builder.forModule(
                new DomainAppAppDefnModule()
            )
            .withAdditionalModules(

                SecurityModule.class,       // expected by shiro config
                CommandModule.class,        // expected by quartz config
                SettingsModule.class,        // expected by togglz


                // extensions
                TogglzModule.class,

                // lib
                BaseServicesModule.class,
                FakeDataModule.class,

                // spi
                AuditModule.class,
                //PublishMqModule.class,
                SessionLoggerModule.class,

                // cpt (wicket ui)
                ExcelUiModule.class,
                FullCalendar2UiModule.class,
                Gmap3ApplibModule.class,
                Gmap3ServiceModule.class,
                Gmap3UiModule.class,
                SummernoteUiModule.class,
                PdfjsCptModule.class,
                WickedChartsUiModule.class
            )
            .withAdditionalServices(
                    PasswordEncryptionServiceUsingJBcrypt.class,
                    PermissionsEvaluationServiceAllowBeatsVeto.class
            )
            .withConfigurationPropertiesFile(DomainAppAppManifest.class,
                    "isis.properties",
                    "authentication_shiro.properties",
                    "persistor_datanucleus.properties",
                    "viewer_restfulobjects.properties",
                    "viewer_wicket.properties"
            )
            .withConfigurationPropertiesFile(DomainAppAppManifest.class,
                    "persistor-hsqldb.properties"
            )
            .withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey", "DomainAppEncryptionKey")

                // override as required
            .withConfigurationProperty("isis.viewer.wicket.gmap3.apiKey","XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
            .withConfigurationProperty("isis.services.audit.objects","none")
            .withConfigurationProperty("isis.services.command.actions","none")
            .withConfigurationProperty("isis.services.command.properties","none")
            .withConfigurationProperty("isis.services.publish.objects","none")
            .withConfigurationProperty("isis.services.publish.actions","none")
            .withConfigurationProperty("isis.services.publish.properties","none")
            //.withFixtureScripts(SeedSuperAdministratorRoleAndSvenSuperUser.class)
            ;

    public DomainAppAppManifest() {
        super(BUILDER);
    }

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        // using withFixtureScripts(...) is broken in 1.16.0
        fixtureScripts.add(SeedSuperAdministratorRoleAndSvenSuperUser.class);
    }

}
