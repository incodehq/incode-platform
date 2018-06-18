package org.incode.example.document.dom;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.document.DocumentModule;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class DocumentModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(DocumentModule.class);

    public DocumentModuleDomManifest() {
        super(BUILDER);
    }

}
