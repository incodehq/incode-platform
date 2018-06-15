package org.incode.platform.ext.togglz.integtests;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.togglz.junit.TogglzRule;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.platform.ext.togglz.integtests.app.TogglzExtAppManifest;

import domainapp.modules.base.togglz.TogglzFeature;

public abstract class TogglzModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzFeature.class);

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                TogglzExtAppManifest.BUILDER
                        .withAdditionalModules(ExampleDomDemoDomSubmodule.class)
        );
    }

}
