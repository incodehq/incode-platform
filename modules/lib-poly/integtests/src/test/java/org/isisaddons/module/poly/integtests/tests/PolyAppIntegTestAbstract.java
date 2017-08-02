package org.isisaddons.module.poly.integtests.tests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.poly.app.PolyLibAppManifest;

public abstract class PolyAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new PolyLibAppManifest());
    }

}
