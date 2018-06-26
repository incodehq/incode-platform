package org.incode.example.alias.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.example.alias.demo.shared.dom.AliasDemoObject;
import org.incode.example.alias.demo.shared.fixture.AliasDemoObjectData;
import org.incode.example.alias.integtests.AliasModuleIntegTestAbstract;
import org.incode.example.alias.demo.usage.fixture.DemoObject_withAliases_create2;
import org.incode.example.alias.demo.shared.dom.AliasDemoObjectMenu;

public class AliasDemoObjectMenu_IntegTest extends AliasModuleIntegTestAbstract {

    @Inject
    AliasDemoObjectMenu aliasDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new AliasDemoObjectData.PersistScript(), null);
        fixtureScripts.runFixtureScript(new DemoObject_withAliases_create2(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<AliasDemoObject> all = wrap(aliasDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(AliasDemoObjectData.values().length);
        
        AliasDemoObject aliasDemoObject = wrap(all.get(0));
        Assertions.assertThat(aliasDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(aliasDemoObjectMenu).createDemoObject("Faz");
        
        final List<AliasDemoObject> all = wrap(aliasDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(AliasDemoObjectData.values().length+1);
    }

}