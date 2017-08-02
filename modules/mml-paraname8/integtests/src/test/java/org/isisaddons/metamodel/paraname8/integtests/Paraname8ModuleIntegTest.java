package org.isisaddons.metamodel.paraname8.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.metamodel.paraname8.app.Paraname8MetaModelAppManifest;

public abstract class Paraname8ModuleIntegTest extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new Paraname8MetaModelAppManifest());
    }

}
