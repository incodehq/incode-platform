package org.isisaddons.module.sessionlogger;

import org.apache.isis.applib.AppManifestAbstract;

public class SessionLoggerSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            SessionLoggerModule.class
    );

    public SessionLoggerSpiAppManifest() {
        super(BUILDER);
    }

}
