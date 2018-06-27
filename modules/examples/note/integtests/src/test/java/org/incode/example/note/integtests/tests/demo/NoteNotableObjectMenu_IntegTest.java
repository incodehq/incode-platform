package org.incode.example.note.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.incode.example.note.integtests.NoteModuleIntegTestAbstract;
import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.examples.note.demo.shared.demo.dom.NotableObjectMenu;
import org.incode.examples.note.demo.usage.fixture.NotableObject_withNotes_create3;

public class NoteNotableObjectMenu_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    NotableObjectMenu noteDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NotableObject_withNotes_create3(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<NotableObject> all = wrap(noteDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        NotableObject noteNotableObject = wrap(all.get(0));
        Assertions.assertThat(noteNotableObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(noteDemoObjectMenu).createDemoObject("Faz");
        
        final List<NotableObject> all = wrap(noteDemoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}