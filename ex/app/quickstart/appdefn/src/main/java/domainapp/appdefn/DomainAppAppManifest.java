package domainapp.appdefn;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.audit.AuditModule;
import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.excel.ExcelModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;
import org.isisaddons.module.poly.PolyModule;
import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto;
import org.isisaddons.module.servletapi.ServletApiModule;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;
import org.isisaddons.module.tags.TagsModule;
import org.isisaddons.module.togglz.TogglzModule;
import org.isisaddons.module.xdocreport.dom.XDocReportModule;
import org.isisaddons.wicket.excel.cpt.ui.ExcelUiModule;
import org.isisaddons.wicket.fullcalendar2.cpt.ui.FullCalendar2UiModule;
import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.service.Gmap3ServiceModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;
import org.isisaddons.wicket.pdfjs.cpt.PdfjsCptModule;
import org.isisaddons.wicket.summernote.cpt.ui.SummernoteUiModule;
import org.isisaddons.wicket.wickedcharts.cpt.ui.WickedChartsUiModule;

import org.incode.module.alias.dom.AliasModule;
import org.incode.module.base.services.BaseServicesModule;
import org.incode.module.classification.dom.ClassificationModule;
import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.communications.dom.CommunicationsModuleDomModule;
import org.incode.module.country.dom.CountryModule;
import org.incode.module.docfragment.dom.DocFragmentModuleDomModule;
import org.incode.module.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.module.docrendering.stringinterpolator.dom.StringInterpolatorDocRenderingModule;
import org.incode.module.docrendering.xdocreport.dom.XDocReportDocRenderingModule;
import org.incode.module.document.dom.DocumentModule;
import org.incode.module.note.dom.NoteModule;

import domainapp.modules.simple.SimpleModule;

public class DomainAppAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(

            DomainAppAppDefnModule.class,

            SimpleModule.class,

            PublishMqModule.class,

            /* Comment out to exclude example modules that set up embedded camel: START */
            domainapp.example.embeddedcamel.EmbeddedCamelModule.class,
            /* Comment out to exclude example modules that set up embedded camel: END */

            // extensions
            TogglzModule.class,

            // lib
            BaseServicesModule.class,
            FreemarkerDocRenderingModule.class,
            StringInterpolatorDocRenderingModule.class,
            XDocReportDocRenderingModule.class,
            DocxModule.class,
            ExcelModule.class,
            FakeDataModule.class,
            FreeMarkerModule.class,
            PolyModule.class,
            PdfBoxModule.class,
            ServletApiModule.class,
            StringInterpolatorModule.class,
            XDocReportModule.class,

            // subdomains (dom)
            AliasModule.class,
            ClassificationModule.class,
            CommChannelModule.class,
            CommunicationsModuleDomModule.class,
            CountryModule.class,
            DocFragmentModuleDomModule.class,
            DocumentModule.class,
            NoteModule.class,
            SettingsModule.class,
            TagsModule.class,

            // spi
            AuditModule.class,
            CommandModule.class,
            SecurityModule.class,
            SessionLoggerModule.class,

            // cpt (wicket ui)
            ExcelUiModule.class,
            FullCalendar2UiModule.class,
            Gmap3ApplibModule.class,
            Gmap3ServiceModule.class,
            Gmap3UiModule.class,
            PdfjsCptModule.class,
            SummernoteUiModule.class,
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
    .withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey", "DomainAppEncryptionKey");

    public DomainAppAppManifest() {
        super(BUILDER);
    }

}
