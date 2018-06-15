package org.incode.example.dom.commchannel.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;

import org.incode.example.commchannel.dom.CommChannelModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.ExampleDomModuleCommChannelModule;

/**
 * Bootstrap the application.
 */
public class CommChannelModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            CommChannelModule.class, // dom module
            ExampleDomModuleCommChannelModule.class,
            Gmap3ApplibModule.class,
            Gmap3UiModule.class
    );

    public CommChannelModuleAppManifest() {
        super(BUILDER);
    }

}
