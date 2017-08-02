package org.isisaddons.module.togglz.integtests;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.togglz.junit.TogglzRule;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.togglz.app.TogglzExtAppManifest;

import domainapp.modules.exampledom.ext.togglz.dom.featuretoggle.TogglzDemoFeature;

public abstract class TogglzModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzDemoFeature.class);

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new TogglzExtAppManifest());
    }

}
