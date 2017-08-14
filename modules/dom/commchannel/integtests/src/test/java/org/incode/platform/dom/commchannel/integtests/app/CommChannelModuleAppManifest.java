package org.incode.platform.dom.commchannel.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;

import org.incode.module.commchannel.dom.CommChannelModule;

import org.incode.domainapp.example.dom.dom.commchannel.ExampleDomModuleCommChannelModule;

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
