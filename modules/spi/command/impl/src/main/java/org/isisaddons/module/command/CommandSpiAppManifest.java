package org.isisaddons.module.command;

import org.apache.isis.applib.AppManifestAbstract;

public class CommandSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            CommandModule.class
    );

    public CommandSpiAppManifest() {
        super(BUILDER);
    }

}
