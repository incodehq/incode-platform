package org.incode.domainapp.example.dom;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class ExampleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            ExampleDomSubmodule.class
    );

    public ExampleDomManifest() {
        super(BUILDER);
    }


}
