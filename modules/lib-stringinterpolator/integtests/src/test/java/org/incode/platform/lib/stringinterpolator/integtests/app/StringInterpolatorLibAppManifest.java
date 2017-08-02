package org.incode.platform.lib.stringinterpolator.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;

import domainapp.modules.exampledom.lib.stringinterpolator.ExampleDomLibStringInterpolatorModule;

public class StringInterpolatorLibAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            StringInterpolatorModule.class,
            ExampleDomLibStringInterpolatorModule.class
    );

    public StringInterpolatorLibAppManifest() {
        super(BUILDER);
    }

}
