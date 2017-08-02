package org.incode.platform.dom.alias.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.alias.fixture.AliasDemoObjectsFixture;

/**
 * Run the dom but without setting up any fixtures.
 */
public class AliasModuleAppManifestWithFixtures extends AliasModuleAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(AliasDemoObjectsFixture.class);
    }

}
