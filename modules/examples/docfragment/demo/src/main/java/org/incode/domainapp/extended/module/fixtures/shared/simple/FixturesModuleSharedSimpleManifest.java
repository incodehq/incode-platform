package org.incode.examples.commchannel.demo.shared.simple;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class FixturesModuleSharedSimpleManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            FixturesModuleSharedSimpleSubmodule.class
    );

    public FixturesModuleSharedSimpleManifest() {
        super(BUILDER);
    }


}
