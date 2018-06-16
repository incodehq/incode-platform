package org.incode.extended.integtests.examples.document.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.extended.integtests.examples.document.dom.document.fixture.DemoObjectWithUrl_and_OtherObject_and_docrefdata_recreate;

/**
 * Run the app but without setting up any fixtures.
 */
public class DocumentModuleAppManifestWithFixtures extends DocumentModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObjectWithUrl_and_OtherObject_and_docrefdata_recreate.class);
    }


}
