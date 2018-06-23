package org.incode.domainapp.extended.integtests.examples.document.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.integtests.examples.document.DocumentModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrlMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.fixture.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.fixture.DemoObjectWithUrl_tearDown;
import org.incode.example.alias.demo.examples.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

public class DemoObjectWithUrlMenu_IntegTest extends DocumentModuleIntegTestAbstract {

    @Inject
    DemoObjectWithUrlMenu demoObjectMenu;

    public static final int NUMBER = 5;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DocumentTypeAndTemplatesApplicableForDemoObjectFixture(), null);
        fixtureScripts.runFixtureScript(new DemoObjectWithUrl_tearDown(), null);
        fixtureScripts.runFixtureScript(new DemoObjectWithUrl_createUpTo5_fakeData().setNumber(NUMBER), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObjectWithUrl> demoObjects = wrap(demoObjectMenu).listAllDemoObjectsWithUrl();
        Assertions.assertThat(demoObjects.size()).isEqualTo(NUMBER);

        for (DemoObjectWithUrl demoObject : demoObjects) {
            Assertions.assertThat(wrap(demoObject).getName()).isNotNull();
            Assertions.assertThat(wrap(demoObject).getUrl()).isNotNull();

        }
    }
    


}