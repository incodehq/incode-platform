package org.incode.domainapp.extended.integtests.spi.publishmq.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.publishmq.PublishMqModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.FixturesModuleSpiPublishMqSubmodule;

public class PublishMqSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(PublishMqModule.class,
            FixturesModuleSpiPublishMqSubmodule.class,
            PublishMqAppModule.class);

    public PublishMqSpiAppManifest() {
        super(BUILDER);
    }

}
