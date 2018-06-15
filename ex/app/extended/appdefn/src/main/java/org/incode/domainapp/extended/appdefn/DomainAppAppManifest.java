package org.incode.domainapp.extended.appdefn;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.audit.AuditModule;
import org.isisaddons.module.publishmq.PublishMqModule;
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

import org.incode.module.base.services.BaseServicesModule;

import org.incode.domainapp.extended.module.fixtures.shared.simple.SimpleModule;

public class DomainAppAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifestAbstract.BUILDER.withAdditionalModules(

            SimpleModule.class,

            DomainAppAppDefnModule.class,

            org.incode.domainapp.extended.embeddedcamel.EmbeddedCamelModule.class,

            // extensions
            TogglzModule.class,

            // lib
            BaseServicesModule.class,

            // spi
            AuditModule.class,
            PublishMqModule.class,
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
    // override as required
    .withConfigurationProperty("isis.viewer.wicket.gmap3.apiKey","XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    ;

    public DomainAppAppManifest() {
        super(BUILDER);
    }

}
