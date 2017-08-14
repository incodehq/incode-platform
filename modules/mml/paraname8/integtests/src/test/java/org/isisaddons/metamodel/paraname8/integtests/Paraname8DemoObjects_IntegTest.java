package org.isisaddons.metamodel.paraname8.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.demo.fixture.data.DemoObjectData;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectTearDown;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Paraname8DemoObjects_IntegTest extends Paraname8ModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObjectTearDown(), null);
        fixtureScripts.runFixtureScript(new DemoObjectData.PersistScript(), null);
    }

    @Inject
    private DemoObjectMenu demoObjectMenu;

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAll();
        assertThat(all.size(), is(DemoObjectData.values().length));
        
        DemoObject paraname8DemoObject = wrap(all.get(0));
        assertThat(paraname8DemoObject.getName(), is("Foo"));
    }
    

}