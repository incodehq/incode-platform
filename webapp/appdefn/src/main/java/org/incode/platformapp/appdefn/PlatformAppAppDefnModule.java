package org.incode.platformapp.appdefn;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

import org.isisaddons.module.audit.AuditModule;
import org.isisaddons.module.audit.fixture.AuditFixturesModule;
import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.command.fixture.CommandFixturesModule;
import org.isisaddons.module.command.replay.CommandReplayModule;
import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.docx.fixture.DocxFixturesModule;
import org.isisaddons.module.excel.ExcelModule;
import org.isisaddons.module.excel.fixture.ExcelFixturesModule;
import org.isisaddons.module.fakedata.fixture.FakeDataFixturesModule;
import org.isisaddons.module.flywaydb.fixture.FlywayDbFixturesModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;
import org.isisaddons.module.poly.PolyModule;
import org.isisaddons.module.poly.fixture.PolyFixturesModule;
import org.isisaddons.module.publishmq.dom.jdo.PublishMqSpiJdoModule;
import org.isisaddons.module.publishmq.dom.servicespi.PublishMqSpiServicesModule;
import org.isisaddons.module.publishmq.fixture.PublishMqFixturesModule;
import org.isisaddons.module.quartz.dom.QuartzModule;
import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.module.security.fixture.SecurityFixturesModule;
import org.isisaddons.module.servletapi.ServletApiModule;
import org.isisaddons.module.servletapi.fixture.ServletApiFixturesModule;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.StringInterpolatorFixturesSubmodule;
import org.isisaddons.module.togglz.TogglzModule;
import org.isisaddons.module.togglz.fixture.TogglzFixturesModule;
import org.isisaddons.module.xdocreport.dom.XDocReportModule;
import org.isisaddons.wicket.excel.cpt.ui.ExcelUiModule;
import org.isisaddons.wicket.fullcalendar2.cpt.ui.FullCalendar2UiModule;
import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.pdfjs.cpt.PdfjsCptModule;
import org.isisaddons.wicket.pdfjs.fixture.PdfjsFixturesModule;
import org.isisaddons.wicket.summernote.cpt.ui.SummernoteUiModule;
import org.isisaddons.wicket.wickedcharts.cpt.ui.WickedChartsUiModule;
import org.isisaddons.wicket.wickedcharts.fixture.WickedChartsFixturesModule;

import org.incode.module.base.services.BaseServicesModule;
import org.incode.module.errorrptjira.ErrorReportingJiraModule;
import org.incode.module.errorrptslack.ErrorReportingSlackModule;
import org.incode.module.jaxrsclient.JaxRsClientModule;
import org.incode.module.settings.SettingsModule;
import org.incode.module.slack.SlackModule;
import org.incode.module.userimpersonate.UserImpersonateModule;
import org.incode.module.zip.ZipModule;
import org.incode.platformapp.appdefn.togglz.TogglzFeatureProviderModule;

/**
 * also add:
 *
 * -Disis.viewer.wicket.gmap3.apiKey=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
 * -Dorg.incode.example.commchannel.dom.api.GeocodingService.apiKey=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
 *
 */
@XmlRootElement(name = "module")
public class PlatformAppAppDefnModule extends ModuleAbstract {

    public PlatformAppAppDefnModule() {
        withConfigurationPropertiesFile(getClass(),
                "isis.properties",
                "authentication_shiro.properties",
                "persistor_datanucleus.properties",
                "viewer_restfulobjects.properties",
                "viewer_wicket.properties"
        );
        withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey", "ExtendedAppEncryptionKey");
    }

    @Override
    public Set<Class<?>> getAdditionalServices() {
        // necessary because of ISIS-1710
        return Sets.newHashSet(
                PasswordEncryptionServiceUsingJBcrypt.class,
                PermissionsEvaluationServiceAllowBeatsVeto.class);
    }

    @Override
    public Set<Module> getDependencies() {
        final HashSet<Module> dependencies = Sets.newHashSet(
                new TogglzFeatureProviderModule(),
                new FlywayDbFixturesModule(),
                new CommandModule(),    // expected by quartz config
                new SecurityModule()    // expected by shiro config
        );

        appendExtModulesTo(dependencies);
        appendLibModulesTo(dependencies);
        appendSpiModulesTo(dependencies);
        appendWktModulesTo(dependencies);

        return dependencies;
    }


    private void appendExtModulesTo(Set<Module> dependencies) {
        final HashSet<ModuleAbstract> mods = Sets.newHashSet(
                new SettingsModule(),   // implementation of togglz provider
                new QuartzModule(),
                new TogglzModule(),
                new TogglzFixturesModule()
        );
        dependencies.addAll(mods);
    }

    private void appendLibModulesTo(Set<Module> dependencies) {
        final HashSet<ModuleAbstract> mods = Sets.newHashSet(
                new BaseServicesModule(),

                new DocxModule(),
                new DocxFixturesModule(),

                new ExcelModule(),
                new ExcelFixturesModule(),

                new FakeDataFixturesModule(),

                new FreeMarkerModule(),
                new JaxRsClientModule(),
                new PdfBoxModule(),

                new PolyModule(),
                new PolyFixturesModule(),

                new ServletApiModule(),
                new ServletApiFixturesModule(),

                new SlackModule(),

                new StringInterpolatorModule(),
                new StringInterpolatorFixturesSubmodule(),

                new XDocReportModule(),
                new ZipModule()
        );
        dependencies.addAll(mods);
    }

    private void appendSpiModulesTo(Set<Module> dependencies) {
        final HashSet<ModuleAbstract> mods = Sets.newHashSet(
                new AuditModule(),
                new AuditFixturesModule(),

                new CommandModule(),            // expected by quartz config
                new CommandFixturesModule(),

                new CommandReplayModule(),
                new ErrorReportingJiraModule(),
                new ErrorReportingSlackModule(),

                new PublishMqSpiServicesModule(),
                new PublishMqSpiJdoModule(),
                new PublishMqFixturesModule(),

                new SecurityModule(),            // expected by shiro config
                new SecurityFixturesModule(),

                new SessionLoggerModule(),
                new UserImpersonateModule()
        );
        dependencies.addAll(mods);
    }

    private void appendWktModulesTo(Set<Module> dependencies) {
        final HashSet<ModuleAbstract> mods = Sets.newHashSet(
                new ExcelUiModule(),
                new FullCalendar2UiModule(),
                new Gmap3ApplibModule(),
                new SummernoteUiModule(),

                new PdfjsCptModule(),
                new PdfjsFixturesModule(),

                new WickedChartsUiModule(),
                new WickedChartsFixturesModule()
        );
        dependencies.addAll(mods);
    }

    @Override
    public Map<String, String> getIndividualConfigProps() {
        final Map<String, String> props = Maps.newHashMap();
        disableFlyway(props);
        disableCommandsAuditingAndPublishing(props);
        return props;
    }

    private static void disableCommandsAuditingAndPublishing(final Map<String, String> props) {
        props.put("isis.services.audit.objects","none");
        props.put("isis.services.command.actions","none");
        props.put("isis.services.publish.objects","none");
        props.put("isis.services.publish.actions","none");
        props.put("isis.services.publish.properties","none");
    }

    private static void disableFlyway(final Map<String, String> props) {
        props.put(
                "isis.persistor.datanucleus.impl.javax.jdo.PersistenceManagerFactoryClass",
                "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
        props.put(
                "isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateAll", "true");
    }


    private static void enableFlywayForSqlServer(final Map<String, String> props) {
        props.put(
                "isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateAll", "false");
        props.put(
                "isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateConstraints", "true");
        props.put(
                "isis.persistor.datanucleus.impl.datanucleus.schema.validateAll", "true");
        props.put(
                "isis.persistor.datanucleus.impl.flyway.locations", "classpath:db/migration/sqlserver");

        props.put(
                "isis.persistor.datanucleus.impl.flyway.schemas",
                Joiner.on(",").join(
                        "flyway",
                        "isissettings",
                        "isisaudit",
                        "isiscommand",
                        "isispublishmq",
                        "isissecurity",
                        "isissessionlogger",
                        "incodeAlias",
                        "incodeClassification",
                        "incodeCommChannel",
                        "IncodeCommunications",
                        "incodeCountry",
                        "incodeDocFragment",
                        "incodeDocuments",
                        "incodeNote",
                        "isistags",
                        "simple"
                ));

        // pick up sqlserver.orm files
        props.put(
                "isis.persistor.datanucleus.impl.datanucleus.Mapping", "sqlserver");

        // JDBC config properties for sqlserver
        props.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionDriverName",
                "com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // specify remaining as system properties or uncomment and hard-code:
        props.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionURL",
                 "jdbc:sqlserver://localhost:1433;instance=.;databaseName=extended_app");
        props.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionUserName", "extended_app_dbo");
        props.put(
                "isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionPassword", "s3cr3t!");
    }
}
