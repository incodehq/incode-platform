package org.incode.platform.lib.docx.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.platform.lib.docx.integtests.app.DocxModuleAppManifest;

public abstract class DocxModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new DocxModuleAppManifest());
    }

}
