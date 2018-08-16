package org.incode.example.classification.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.incode.example.classification.demo.shared.demowithatpath.dom.SomeClassifiedObjectMenu;
import org.incode.example.classification.integtests.ClassificationModuleIntegTestAbstract;
import org.incode.example.classification.demo.shared.demowithatpath.dom.SomeClassifiedObject;
import org.incode.example.classification.demo.usage.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3;

public class DemoObjectMenu_IntegTest extends ClassificationModuleIntegTestAbstract {

    DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3 fs;

    @Before
    public void setUpData() throws Exception {
        fs = new DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3();
        fixtureScripts.runFixtureScript(fs, null);
    }

    @Ignore // TODO: to fix...
    @Test
    public void listAll() throws Exception {

        // given
        int numDemoObjects = fs.getDemoObjects().size();

        // when
        final List<SomeClassifiedObject> all = wrap(demoObjectMenu).listAllOfSomeClassifiedObjects();

        // then
        Assertions.assertThat(all.size()).isEqualTo(numDemoObjects);
    }
    
    @Test
    public void create() throws Exception {

        // given
        final List<SomeClassifiedObject> before = wrap(demoObjectMenu).listAllOfSomeClassifiedObjects();
        int numBefore = before.size();

        // when
        wrap(demoObjectMenu).createSomeClassifiedObject("Faz", "/");

        // then
        final List<SomeClassifiedObject> after = wrap(demoObjectMenu).listAllOfSomeClassifiedObjects();
        Assertions.assertThat(after.size()).isEqualTo(numBefore+1);
    }

    @Inject
    SomeClassifiedObjectMenu demoObjectMenu;


}