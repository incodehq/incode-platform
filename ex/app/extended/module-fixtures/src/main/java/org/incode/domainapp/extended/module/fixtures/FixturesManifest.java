package org.incode.domainapp.extended.module.fixtures;

import org.apache.isis.applib.AppManifestAbstract2;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class FixturesManifest extends AppManifestAbstract2 {

    public static final Builder BUILDER = Builder.forModule(
            new FixturesModule()
    );

    public FixturesManifest() {
        super(BUILDER);
    }

}
