package org.incode.example.commchannel.dom;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class CommChannelModuleDomManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            CommChannelModule.class  // domain (entities and repositories)
    );

    public CommChannelModuleDomManifest() {
        super(BUILDER);
    }


}
