package org.incode.domainapp.extended.integtests.examples.note.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.integtests.examples.note.NoteModuleIntegTestAbstract;
import org.incode.example.alias.demo.examples.note.fixture.DemoObject_withNotes_create3;
import org.incode.example.alias.demo.shared.dom.DemoObject;
import org.incode.example.alias.demo.shared.dom.DemoObjectMenu;

public class NoteDemoObjectMenu_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu noteDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withNotes_create3(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(noteDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        DemoObject noteDemoObject = wrap(all.get(0));
        Assertions.assertThat(noteDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(noteDemoObjectMenu).createDemoObject("Faz");
        
        final List<DemoObject> all = wrap(noteDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}