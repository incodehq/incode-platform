package org.incode.platform.lib.docx.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.order.fixture.DemoOrderAndOrderLine_recreate4_hardcodedData;

public class DocxModuleAppManifestWithFixtures extends DocxModuleAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoOrderAndOrderLine_recreate4_hardcodedData.class);
    }
}
