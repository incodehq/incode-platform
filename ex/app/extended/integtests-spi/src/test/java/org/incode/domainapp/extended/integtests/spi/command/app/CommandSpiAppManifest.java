package org.incode.domainapp.extended.integtests.spi.command.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.command.CommandModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.FixturesModuleSpiCommandSubmodule;

public class CommandSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            CommandModule.class,
            FixturesModuleSpiCommandSubmodule.class
    );

    public CommandSpiAppManifest() {
        super(BUILDER);
    }

}
