package org.incode.platform.spi.publishmq.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.publishmq.PublishMqModule;

import domainapp.modules.exampledom.spi.publishmq.ExampleDomSpiPublishMqModule;

public class PublishMqSpiAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(PublishMqModule.class,
            ExampleDomSpiPublishMqModule.class,
            PublishMqAppModule.class);

    public PublishMqSpiAppManifest() {
        super(BUILDER);
    }

}
