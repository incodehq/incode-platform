package org.incode.extended.integtests.examples.document.integtests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.fixture.DemoModule_and_DocTypesAndTemplates_tearDown;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrl;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.dom.DemoObjectWithUrlMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demowithurl.fixture.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.extended.integtests.examples.document.DocumentModuleIntegTestAbstract;

public class DemoObjectMenu_IntegTest extends DocumentModuleIntegTestAbstract {

    @Inject
    DemoObjectWithUrlMenu demoObjectMenu;

    public static final int NUMBER = 5;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoModule_and_DocTypesAndTemplates_tearDown(), null);
        fixtureScripts.runFixtureScript(new DocumentTypeAndTemplatesApplicableForDemoObjectFixture(), null);
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