package org.incode.module.alias.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.alias.fixture.AliasDemoObjectsFixture;

/**
 * Run the dom but without setting up any fixtures.
 */
public class AliasModuleAppManifestWithFixtures extends AliasModuleAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(AliasDemoObjectsFixture.class);
    }

}
