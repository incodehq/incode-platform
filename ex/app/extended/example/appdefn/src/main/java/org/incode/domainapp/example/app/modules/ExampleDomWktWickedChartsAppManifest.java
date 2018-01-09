package org.incode.domainapp.example.app.modules;

import org.isisaddons.wicket.wickedcharts.cpt.ui.WickedChartsUiModule;

import org.incode.domainapp.example.dom.wkt.wickedcharts.ExampleDomWicketWickedChartsModule;

public class ExampleDomWktWickedChartsAppManifest extends DemoToDoItemAppManifestAbstract {

    public static final Builder BUILDER = DemoToDoItemAppManifestAbstract.BUILDER.withAdditionalModules(
            WickedChartsUiModule.class,
            ExampleDomWicketWickedChartsModule.class
        );

    public ExampleDomWktWickedChartsAppManifest() {
        super(BUILDER);
    }

}
