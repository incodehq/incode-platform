package org.incode.example.note.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.fixture.DemoObject_withNotes_recreate3;

/**
 * Run the app but without setting up any fixtures.
 */
public class NoteModuleAppManifestWithFixtures extends NoteModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObject_withNotes_recreate3.class);
    }


}
