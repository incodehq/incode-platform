package org.incode.domainapp.extended.integtests.examples.note.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.note.dom.NoteModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.FixturesModuleExamplesNoteSubmodule;

/**
 * Bootstrap the application.
 */
public class NoteModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            NoteModule.class, // dom module
            FixturesModuleExamplesNoteSubmodule.class,
            NoteAppModule.class
    );

    public NoteModuleAppManifest() {
        super(BUILDER);
    }

}
