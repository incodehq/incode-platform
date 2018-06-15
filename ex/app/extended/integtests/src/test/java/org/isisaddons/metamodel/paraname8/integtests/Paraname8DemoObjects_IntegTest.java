package org.isisaddons.metamodel.paraname8.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.domainapp.extended.module.fixtures.shared.demo.fixture.DemoObjectData;
import org.incode.domainapp.extended.module.fixtures.shared.demo.fixture.DemoObject_tearDown;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Paraname8DemoObjects_IntegTest extends Paraname8ModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_tearDown(), null);
        fixtureScripts.runFixtureScript(new DemoObjectData.PersistScript(), null);
    }

    @Inject
    private DemoObjectMenu demoObjectMenu;

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        assertThat(all.size(), is(DemoObjectData.values().length));
        
        DemoObject paraname8DemoObject = wrap(all.get(0));
        assertThat(paraname8DemoObject.getName(), is("Foo"));
    }
    

}