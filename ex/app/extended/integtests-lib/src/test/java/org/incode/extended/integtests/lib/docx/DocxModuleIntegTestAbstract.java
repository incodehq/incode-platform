package org.incode.extended.integtests.lib.docx;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.extended.integtests.lib.docx.app.DocxModuleAppManifest;

public abstract class DocxModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                DocxModuleAppManifest.BUILDER
                        .withAdditionalModules(ExampleDomDemoDomSubmodule.class)
        );
    }

}
