package org.incode.platform.lib.docx.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.docx.DocxModule;

import org.incode.domainapp.module.fixtures.per_cpt.lib.docx.ExampleDomLibDocxModule;

public class DocxModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(DocxAppModule.class,
            ExampleDomLibDocxModule.class,
            DocxModule.class);

    public DocxModuleAppManifest() {
        super(BUILDER);
    }

}
