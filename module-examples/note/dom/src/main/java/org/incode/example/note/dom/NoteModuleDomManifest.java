package org.incode.example.note.dom;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.note.NoteModule;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class NoteModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            NoteModule.class  // domain (entities and repositories)
    );

    public NoteModuleDomManifest() {
        super(BUILDER);
    }

}
