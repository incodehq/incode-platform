package org.incode.platform.spi.sessionlogger.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

import org.incode.domainapp.example.dom.spi.sessionlogger.ExampleDomSpiSessionLoggerModule;

public class SessionLoggerAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            SessionLoggerModule.class,
            ExampleDomSpiSessionLoggerModule.class
    );

    public SessionLoggerAppManifest() {
        super(BUILDER);
    }

}
