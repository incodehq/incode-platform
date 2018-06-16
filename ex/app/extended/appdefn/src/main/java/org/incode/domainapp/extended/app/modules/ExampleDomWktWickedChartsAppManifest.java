package org.incode.domainapp.extended.app.modules;

import org.isisaddons.wicket.wickedcharts.cpt.ui.WickedChartsUiModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.wicketcharts.FixturesModuleWktWickedChartsSubmodule;

public class ExampleDomWktWickedChartsAppManifest extends DemoToDoItemAppManifestAbstract {

    public static final Builder BUILDER = DemoToDoItemAppManifestAbstract.BUILDER.withAdditionalModules(
            WickedChartsUiModule.class,
            FixturesModuleWktWickedChartsSubmodule.class
        );

    public ExampleDomWktWickedChartsAppManifest() {
        super(BUILDER);
    }

}
