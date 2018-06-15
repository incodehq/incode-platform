package org.incode.extended.integtests.lib.poly;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.extended.integtests.lib.poly.app.PolyLibAppManifest;

public abstract class PolyAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new PolyLibAppManifest());
    }

}
