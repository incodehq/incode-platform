package org.isisaddons.module.security;

import org.apache.isis.applib.AppManifestAbstract;

public class SecuritySpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            SecurityModule.class
    );

    public SecuritySpiAppManifest() {
        super(BUILDER);
    }

}
