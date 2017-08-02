package org.isisaddons.module.publishmq.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.spi.publishmq.fixture.PublishMqDemoObjectsFixture;

public class PublishMqSpiAppManifestWithFixtures extends PublishMqSpiAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(PublishMqDemoObjectsFixture.class);
    }
}
