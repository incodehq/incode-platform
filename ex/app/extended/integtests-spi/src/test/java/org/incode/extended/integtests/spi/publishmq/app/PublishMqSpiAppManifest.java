package org.incode.extended.integtests.spi.publishmq.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.publishmq.PublishMqModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.ExampleDomSpiPublishMqModule;

public class PublishMqSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(PublishMqModule.class,
            ExampleDomSpiPublishMqModule.class,
            PublishMqAppModule.class);

    public PublishMqSpiAppManifest() {
        super(BUILDER);
    }

}
