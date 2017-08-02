package org.incode.module.document.integtests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import domainapp.modules.exampledom.module.document.dom.demo.DemoObject;
import domainapp.modules.exampledom.module.document.dom.demo.DemoObjectMenu;
import domainapp.modules.exampledom.module.document.fixture.data.DemoObjectsFixture;
import domainapp.modules.exampledom.module.document.fixture.DocumentDemoAppTearDownFixture;
import domainapp.modules.exampledom.module.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.module.document.integtests.DocumentModuleIntegTestAbstract;

public class DemoObjectMenu_IntegTest extends DocumentModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu demoObjectMenu;

    public static final int NUMBER = 5;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DocumentDemoAppTearDownFixture(), null);
        fixtureScripts.runFixtureScript(new DocumentTypeAndTemplatesApplicableForDemoObjectFixture(), null);
        fixtureScripts.runFixtureScript(new DemoObjectsFixture().setNumber(NUMBER), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> demoObjects = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(demoObjects.size()).isEqualTo(NUMBER);

        for (DemoObject demoObject : demoObjects) {
            Assertions.assertThat(wrap(demoObject).getName()).isNotNull();
            Assertions.assertThat(wrap(demoObject).getUrl()).isNotNull();

        }
    }
    


}