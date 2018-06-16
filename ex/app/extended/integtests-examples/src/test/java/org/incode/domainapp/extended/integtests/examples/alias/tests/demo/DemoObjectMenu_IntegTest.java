package org.incode.domainapp.extended.integtests.examples.alias.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.integtests.examples.alias.AliasModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.shared.demo.fixture.DemoObjectData;
import org.incode.domainapp.extended.integtests.examples.alias.integration.fixture.DemoObject_withAliases_recreate2;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;

public class DemoObjectMenu_IntegTest extends AliasModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withAliases_recreate2(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(DemoObjectData.values().length);
        
        DemoObject demoObject = wrap(all.get(0));
        Assertions.assertThat(demoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).createDemoObject("Faz");
        
        final List<DemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(DemoObjectData.values().length+1);
    }

}