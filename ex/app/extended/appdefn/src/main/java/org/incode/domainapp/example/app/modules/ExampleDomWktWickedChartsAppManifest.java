package org.incode.domainapp.example.app.modules;

import org.isisaddons.wicket.wickedcharts.cpt.ui.WickedChartsUiModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.wicketcharts.ExampleDomWicketWickedChartsModule;

public class ExampleDomWktWickedChartsAppManifest extends DemoToDoItemAppManifestAbstract {

    public static final Builder BUILDER = DemoToDoItemAppManifestAbstract.BUILDER.withAdditionalModules(
            WickedChartsUiModule.class,
            ExampleDomWicketWickedChartsModule.class
        );

    public ExampleDomWktWickedChartsAppManifest() {
        super(BUILDER);
    }

}
