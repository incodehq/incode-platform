package org.incode.platform.lib.poly.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.poly.PolyModule;

import org.incode.domainapp.example.dom.lib.poly.ExampleDomLibPolyModule;

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
