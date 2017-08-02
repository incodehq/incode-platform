package org.incode.module.note.dom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

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
