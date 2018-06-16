package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class ExampleDomModuleDocFragmentDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            FixturesModuleExamplesDocFragmentSubmodule.class
    );

    public ExampleDomModuleDocFragmentDomManifest() {
        super(BUILDER);
    }

}
