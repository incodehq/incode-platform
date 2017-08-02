package org.isisaddons.module.sessionlogger.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

import domainapp.modules.exampledom.spi.sessionlogger.ExampleDomSpiSessionLoggerModule;

public class SessionLoggerAppManifest extends AppManifestAbstract {

    public SessionLoggerAppManifest() {
        super(Builder.withModules(
                SessionLoggerModule.class,
                ExampleDomSpiSessionLoggerModule.class
        ));
    }

}
