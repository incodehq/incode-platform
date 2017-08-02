package org.incode.module.classification.app;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.module.classification.dom.ClassificationModule;
import domainapp.modules.exampledom.module.classification.ExampleDomModuleClassificationModule;

/**
 * Bootstrap the application.
 */
public class ClassificationModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(
            ClassificationModule.class, // dom module
            ExampleDomModuleClassificationModule.class,
            ClassificationAppModule.class
    );

    public ClassificationModuleAppManifest() {
        super(BUILDER);
    }


}
