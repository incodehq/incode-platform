package org.incode.platform.spi.sessionlogger.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

public class SessionLoggerAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            SessionLoggerModule.class
    );

    public SessionLoggerAppManifest() {
        super(BUILDER);
    }

}
