package org.incode.example.note.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.note.fixture.DemoObject_withNotes_recreate3;

/**
 * Run the app but without setting up any fixtures.
 */
public class NoteModuleAppManifestWithFixtures extends NoteModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObject_withNotes_recreate3.class);
    }


}
