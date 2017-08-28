package org.isisaddons.module.settings;

import org.apache.isis.applib.AppManifestAbstract;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class SettingsManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            SettingsModule.class  // domain (entities and repositories)
    );

    public SettingsManifest() {
        super(BUILDER);
    }

}
