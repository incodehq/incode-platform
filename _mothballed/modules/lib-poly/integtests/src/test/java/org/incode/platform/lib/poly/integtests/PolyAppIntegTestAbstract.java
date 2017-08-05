package org.incode.platform.lib.poly.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.platform.lib.poly.integtests.app.PolyLibAppManifest;

public abstract class PolyAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new PolyLibAppManifest());
    }

}
