package org.incode.platform.ext.togglz.integtests;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.togglz.junit.TogglzRule;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;
import org.incode.domainapp.example.dom.ext.togglz.dom.TogglzDemoFeature;
import org.incode.platform.ext.togglz.integtests.app.TogglzExtAppManifest;

public abstract class TogglzModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzDemoFeature.class);

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                TogglzExtAppManifest.BUILDER
                        .withAdditionalModules(ExampleDemoSubmodule.class)
        );
    }

}
