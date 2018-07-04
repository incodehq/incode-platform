package org.incode.example.communications.dom;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.security.SecurityModule;

import org.incode.example.communications.CommunicationsModule;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class CommunicationsModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            CommunicationsModule.class,
            SecurityModule.class
    );

    public CommunicationsModuleDomManifest() {
        super(BUILDER);
    }

}
