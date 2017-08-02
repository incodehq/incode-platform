package org.incode.platform.spi.sessionlogger.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

import org.incode.domainapp.example.dom.spi.sessionlogger.ExampleDomSpiSessionLoggerModule;

public class SessionLoggerAppManifest extends AppManifestAbstract {

    public SessionLoggerAppManifest() {
        super(Builder.forModules(
                SessionLoggerModule.class,
                ExampleDomSpiSessionLoggerModule.class
        ));
    }

}
