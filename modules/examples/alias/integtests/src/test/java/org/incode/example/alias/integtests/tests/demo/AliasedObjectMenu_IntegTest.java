package org.incode.example.alias.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.example.alias.demo.shared.dom.AliasedObject;
import org.incode.example.alias.demo.shared.fixture.AliasedObjectData;
import org.incode.example.alias.integtests.AliasModuleIntegTestAbstract;
import org.incode.example.alias.demo.usage.fixture.AliasedObject_withAliases_create2;
import org.incode.example.alias.demo.shared.dom.AliasedObjectMenu;

public class AliasedObjectMenu_IntegTest extends AliasModuleIntegTestAbstract {

    @Inject
    AliasedObjectMenu aliasDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new AliasedObjectData.PersistScript(), null);
        fixtureScripts.runFixtureScript(new AliasedObject_withAliases_create2(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<AliasedObject> all = wrap(aliasDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(AliasedObjectData.values().length);
        
        AliasedObject aliasedObject = wrap(all.get(0));
        Assertions.assertThat(aliasedObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(aliasDemoObjectMenu).createDemoObject("Faz");
        
        final List<AliasedObject> all = wrap(aliasDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(AliasedObjectData.values().length+1);
    }

}