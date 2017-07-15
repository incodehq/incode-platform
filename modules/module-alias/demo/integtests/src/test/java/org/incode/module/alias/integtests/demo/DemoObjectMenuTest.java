package org.incode.module.alias.integtests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import domainapp.modules.exampledom.module.alias.dom.demo.DemoObject;
import domainapp.modules.exampledom.module.alias.dom.demo.DemoObjectMenu;
import domainapp.modules.exampledom.module.alias.fixture.AliasDemoObjectsFixture;
import org.incode.module.alias.integtests.AliasModuleIntegTest;

public class DemoObjectMenuTest extends AliasModuleIntegTest {

    @Inject
    DemoObjectMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new AliasDemoObjectsFixture(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        DemoObject demoObject = wrap(all.get(0));
        Assertions.assertThat(demoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).create("Faz");
        
        final List<DemoObject> all = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}