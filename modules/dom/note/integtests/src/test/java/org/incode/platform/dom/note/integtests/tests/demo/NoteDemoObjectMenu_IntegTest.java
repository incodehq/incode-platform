package org.incode.platform.dom.note.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.note.fixture.DemoObject_withNotes_recreate3;
import org.incode.platform.dom.note.integtests.NoteModuleIntegTestAbstract;

public class NoteDemoObjectMenu_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu noteDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withNotes_recreate3(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(noteDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        DemoObject noteDemoObject = wrap(all.get(0));
        Assertions.assertThat(noteDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(noteDemoObjectMenu).create("Faz");
        
        final List<DemoObject> all = wrap(noteDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}