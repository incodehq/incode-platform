package org.isisaddons.metamodel.paraname8.integtests;

import domainapp.modules.exampledom.metamodel.paraname8.dom.fixture.dom.Paraname8DemoObject;
import domainapp.modules.exampledom.metamodel.paraname8.dom.fixture.dom.Paraname8DemoObjects;
import domainapp.modules.exampledom.metamodel.paraname8.dom.fixture.scripts.Paraname8DemoObjectsFixture;

import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Paraname8DemoObjectsTest extends Paraname8ModuleIntegTest {

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new Paraname8DemoObjectsFixture());
    }

    @Inject
    private Paraname8DemoObjects paraname8DemoObjects;

    @Test
    public void listAll() throws Exception {

        final List<Paraname8DemoObject> all = wrap(paraname8DemoObjects).listAll();
        assertThat(all.size(), is(3));
        
        Paraname8DemoObject paraname8DemoObject = wrap(all.get(0));
        assertThat(paraname8DemoObject.getName(), is("Foo"));
    }
    
    @Test
    public void create() throws Exception {

        wrap(paraname8DemoObjects).create("Faz");
        
        final List<Paraname8DemoObject> all = wrap(paraname8DemoObjects).listAll();
        assertThat(all.size(), is(4));
    }

}