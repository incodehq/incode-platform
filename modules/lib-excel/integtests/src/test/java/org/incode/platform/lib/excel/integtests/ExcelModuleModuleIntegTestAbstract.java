package org.incode.platform.lib.excel.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.platform.lib.excel.integtests.app.ExcelLibAppManifest;

public abstract class ExcelModuleModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new ExcelLibAppManifest());
    }

}
