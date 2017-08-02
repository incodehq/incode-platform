package org.incode.module.note.integtests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import domainapp.modules.exampledom.module.note.dom.demo.NoteDemoObject;
import domainapp.modules.exampledom.module.note.dom.demo.NoteDemoObjectMenu;
import domainapp.modules.exampledom.module.note.fixture.NoteDemoObjectsFixture;
import org.incode.module.note.integtests.NoteModuleIntegTestAbstract;

public class NoteDemoObjectMenu_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsFixture(), null);
    }

    @Test
    public void listAll() throws Exception {

        final List<NoteDemoObject> all = wrap(noteDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        NoteDemoObject noteDemoObject = wrap(all.get(0));
        Assertions.assertThat(noteDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(noteDemoObjectMenu).create("Faz");
        
        final List<NoteDemoObject> all = wrap(noteDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}