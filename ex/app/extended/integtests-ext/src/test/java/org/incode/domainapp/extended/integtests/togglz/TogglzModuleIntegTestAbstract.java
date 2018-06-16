package org.incode.domainapp.extended.integtests.togglz;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.togglz.junit.TogglzRule;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.domainapp.extended.integtests.togglz.app.TogglzExtAppManifest;

import org.incode.domainapp.extended.module.base.togglz.TogglzFeature;

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
