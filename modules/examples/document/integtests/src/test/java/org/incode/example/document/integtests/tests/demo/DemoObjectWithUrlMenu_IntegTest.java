package org.incode.example.document.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.example.document.integtests.DocumentModuleIntegTestAbstract;
import org.incode.example.document.demo.usage.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.example.document.demo.shared.demowithurl.dom.DemoObjectWithUrl;
import org.incode.example.document.demo.shared.demowithurl.dom.DemoObjectWithUrlMenu;
import org.incode.example.document.demo.shared.demowithurl.fixture.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.example.document.demo.shared.demowithurl.fixture.DemoObjectWithUrl_tearDown;

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