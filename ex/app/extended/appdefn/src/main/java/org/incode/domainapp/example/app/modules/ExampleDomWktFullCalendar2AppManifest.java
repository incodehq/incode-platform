package org.incode.domainapp.example.app.modules;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.FullCalendar2ApplibModule;
import org.isisaddons.wicket.fullcalendar2.cpt.ui.FullCalendar2UiModule;

public class ExampleDomWktFullCalendar2AppManifest extends DemoToDoItemAppManifestAbstract {

    public static final Builder BUILDER = DemoToDoItemAppManifestAbstract.BUILDER.withAdditionalModules(
                FullCalendar2ApplibModule.class,
                FullCalendar2UiModule.class
            );

    public ExampleDomWktFullCalendar2AppManifest() {
        super(BUILDER);
    }

}
