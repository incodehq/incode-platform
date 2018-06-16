package org.incode.domainapp.extended.module.fixtures;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class FixturesManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            FixturesModule.class
    );

    public FixturesManifest() {
        super(BUILDER);
    }


}
