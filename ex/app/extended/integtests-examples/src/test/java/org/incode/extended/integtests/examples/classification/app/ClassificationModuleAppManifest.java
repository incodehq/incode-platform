package org.incode.extended.integtests.examples.classification.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.classification.dom.ClassificationModule;
import org.incode.extended.integtests.examples.classification.demo.ClassificationModuleDemoDomSubmodule;

/**
 * Bootstrap the application.
 */
public class ClassificationModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            ClassificationModule.class, // dom module
            ClassificationModuleDemoDomSubmodule.class,
            ClassificationAppModule.class
    );

    public ClassificationModuleAppManifest() {
        super(BUILDER);
    }


}
