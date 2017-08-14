package org.incode.platform.lib.fakedata.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;
import org.incode.platform.lib.fakedata.integtests.app.FakedataLibAppManifest;

public abstract class FakeDataModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(FakedataLibAppManifest.BUILDER
                .withAdditionalModules(ExampleDemoSubmodule.class)
        );
    }

}
