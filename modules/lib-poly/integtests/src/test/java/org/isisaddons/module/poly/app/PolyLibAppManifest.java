package org.isisaddons.module.poly.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.poly.PolyModule;

import domainapp.modules.exampledom.lib.poly.ExampleDomLibPolyModule;

public class PolyLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER =
            Builder.forModules(
                    PolyModule.class,
                    ExampleDomLibPolyModule.class,
                    PolyAppModule.class
            );

    public PolyLibAppManifest() {
        super(BUILDER);
    }
}
