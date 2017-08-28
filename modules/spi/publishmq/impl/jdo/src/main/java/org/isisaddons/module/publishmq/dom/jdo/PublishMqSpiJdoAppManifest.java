package org.isisaddons.module.publishmq.dom.jdo;

import org.apache.isis.applib.AppManifestAbstract;

public class PublishMqSpiJdoAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            PublishMqSpiJdoModule.class
    );

    public PublishMqSpiJdoAppManifest() {
        super(BUILDER);
    }

}
