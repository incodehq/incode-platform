package org.incode.example.document.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.example.document.integtests.DocumentModuleIntegTestAbstract;
import org.incode.example.document.demo.usage.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;
import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrlMenu;
import org.incode.example.document.demo.shared.demowithurl.fixture.DocDemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.example.document.demo.shared.demowithurl.fixture.DocDemoObjectWithUrl_tearDown;

public class DocDocDemoObjectWithUrlMenu_IntegTest extends DocumentModuleIntegTestAbstract {

    @Inject
    DocDemoObjectWithUrlMenu demoObjectMenu;

    public static final int NUMBER = 5;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DocumentTypeAndTemplatesApplicableForDemoObjectFixture(), null);
        fixtureScripts.runFixtureScript(new DocDemoObjectWithUrl_tearDown(), null);
        fixtureScripts.runFixtureScript(new DocDemoObjectWithUrl_createUpTo5_fakeData().setNumber(NUMBER), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DocDemoObjectWithUrl> demoObjects = wrap(demoObjectMenu).listAllDemoObjectsWithUrl();
        Assertions.assertThat(demoObjects.size()).isEqualTo(NUMBER);

        for (DocDemoObjectWithUrl demoObject : demoObjects) {
            Assertions.assertThat(wrap(demoObject).getName()).isNotNull();
            Assertions.assertThat(wrap(demoObject).getUrl()).isNotNull();

        }
    }
    


}