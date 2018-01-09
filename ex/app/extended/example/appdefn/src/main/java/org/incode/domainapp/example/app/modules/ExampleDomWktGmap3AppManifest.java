package org.incode.domainapp.example.app.modules;

import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.service.Gmap3ServiceModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;

public class ExampleDomWktGmap3AppManifest extends DemoToDoItemAppManifestAbstract {

    public static final Builder BUILDER = DemoToDoItemAppManifestAbstract.BUILDER.withAdditionalModules(

            Gmap3ApplibModule.class,
            Gmap3ServiceModule.class,
            Gmap3UiModule.class

            // also add:
            // -Disis.viewer.wicket.gmap3.apiKey=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    );

    public ExampleDomWktGmap3AppManifest() {
        super(BUILDER);
    }

}
