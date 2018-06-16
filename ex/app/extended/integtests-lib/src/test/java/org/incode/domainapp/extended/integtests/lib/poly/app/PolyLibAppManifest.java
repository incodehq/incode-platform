package org.incode.domainapp.extended.integtests.lib.poly.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.poly.PolyModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.FixturesModuleLibPolySubmodule;

public class PolyLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER =
            Builder.forModules(
                    PolyModule.class,
                    FixturesModuleLibPolySubmodule.class,
                    PolyAppModule.class
            );

    public PolyLibAppManifest() {
        super(BUILDER);
    }
}
