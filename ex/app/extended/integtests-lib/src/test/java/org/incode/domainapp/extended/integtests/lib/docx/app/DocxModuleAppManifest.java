package org.incode.domainapp.extended.integtests.lib.docx.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.docx.DocxModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.FixturesModuleLibDocxSubmodule;

public class DocxModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(DocxAppModule.class,
            FixturesModuleLibDocxSubmodule.class,
            DocxModule.class);

    public DocxModuleAppManifest() {
        super(BUILDER);
    }

}
