package org.incode.extended.integtests.mml.paraname8.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.extended.integtests.mml.paraname8.app.Paraname8MetaModelAppManifest;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;

public abstract class Paraname8ModuleIntegTest extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(Paraname8MetaModelAppManifest.BUILDER
                .withAdditionalModules(FixturesModuleSharedSubmodule.class)
        );
    }

}
