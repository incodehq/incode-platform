package org.incode.example.docfragment;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.docfragment.dom.DocFragmentModuleDomModule;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class DocFragmentModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            DocFragmentModuleDomModule.class
    );

    public DocFragmentModuleDomManifest() {
        super(BUILDER);
    }

}
