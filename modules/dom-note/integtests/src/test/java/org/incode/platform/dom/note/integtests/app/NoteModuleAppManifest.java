package org.incode.platform.dom.note.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.module.note.dom.NoteModule;

import domainapp.modules.exampledom.module.note.ExampleDomModuleNoteModule;

/**
 * Bootstrap the application.
 */
public class NoteModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            NoteModule.class, // dom module
            ExampleDomModuleNoteModule.class,
            NoteAppModule.class
    );

    public NoteModuleAppManifest() {
        super(BUILDER);
    }

}
