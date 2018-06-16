package org.incode.domainapp.extended.integtests.examples.commchannel.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;

import org.incode.example.commchannel.dom.CommChannelModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.FixturesModuleExamplesCommChannelSubmodule;

/**
 * Bootstrap the application.
 */
public class CommChannelModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            CommChannelModule.class, // dom module
            FixturesModuleExamplesCommChannelSubmodule.class,
            Gmap3ApplibModule.class,
            Gmap3UiModule.class
    );

    public CommChannelModuleAppManifest() {
        super(BUILDER);
    }

}
