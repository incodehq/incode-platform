package org.incode.module.note.integtests.note;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.Note_remove;
import domainapp.modules.exampledom.module.note.dom.spiimpl.CalendarNameRepositoryForDemo;
import domainapp.modules.exampledom.module.note.dom.demo.NoteDemoObject;
import domainapp.modules.exampledom.module.note.dom.demo.NoteDemoObjectMenu;
import domainapp.modules.exampledom.module.note.fixture.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Note_remove_IntegTest extends NoteModuleIntegTest {

    //region > injected services
    //endregion

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    NoteDemoObject notable;
    Note note;
    Note noteWithoutDate;
    Note noteWithoutText;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        notable = wrap(noteDemoObjectMenu).create("Foo");
        calendarNameRepository.setCalendarNames(NoteDemoObject.class, "BLUE", "GREEN", "RED");

        wrap(mixinAddNote(notable)).$$("note A", fakeData.jodaLocalDates().any(), "GREEN");
        wrap(mixinAddNote(notable)).$$("note B", null, null);
        wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "RED");

        final List<Note> noteList = wrap(mixinNotes(notable)).$$();
        note = Iterables.find(noteList, x -> x.getContent() != null && x.getDate() != null);
        noteWithoutDate = Iterables.find(noteList, x -> x.getDate() == null);
        noteWithoutText = Iterables.find(noteList, x -> x.getContent() == null);
    }

    String anyOtherCalendarNameFor(final Object notable, final String exclude) {
        for (String calendarName : calendarNameRepository.calendarNamesFor(notable)) {
            if(!calendarName.equals(exclude)) {
                return calendarName;
            }
        }
        throw new IllegalStateException("could not find any other calendar name");
    }


    public static class ActionImplementationIntegTest extends Note_remove_IntegTest {

        @Test
        public void happy_case() throws Exception {

            // given
            final List<Note> noteList = wrap(mixinNotes(notable)).$$();
            assertThat(noteList).hasSize(3);

            // when
            wrap(mixinRemove(note)).$$();

            // then
            final List<Note> noteListAfter = wrap(mixinNotes(notable)).$$();
            assertThat(noteListAfter).hasSize(2);

        }
    }

    public static class DomainEventIntegTest extends Note_remove_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class Subscriber extends AbstractSubscriber {

            Note_remove.DomainEvent ev;

            @Subscribe
            public void on(Note_remove.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        Subscriber subscriber;

        @Test
        public void fires_event() throws Exception {

            // when
            final Note_remove mixinRemove = mixinRemove(note);
            wrap(mixinRemove).$$();

            // then
            assertThat(subscriber.ev).isNotNull();
            assertThat(subscriber.ev.getSource().getNote()).isSameAs(note);

            // no longer true, as per automatic dereferencing by wrapper factory in ISIS-1425
            // assertThat(subscriber.ev.getSource()).isSameAs(mixinRemove);

        }
    }

}