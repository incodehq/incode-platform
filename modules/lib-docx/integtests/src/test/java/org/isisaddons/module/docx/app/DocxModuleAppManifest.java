package org.isisaddons.module.docx.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.docx.DocxModule;

import domainapp.modules.exampledom.lib.docx.ExampleDomLibDocxModule;

public class DocxModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(DocxAppModule.class,
            ExampleDomLibDocxModule.class,
            DocxModule.class);

    public DocxModuleAppManifest() {
        super(BUILDER);
    }

}
