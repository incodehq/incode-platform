package org.isisaddons.module.fakedata.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.app.FakedataLibAppManifest;

public abstract class FakeDataModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new FakedataLibAppManifest());
    }

}
