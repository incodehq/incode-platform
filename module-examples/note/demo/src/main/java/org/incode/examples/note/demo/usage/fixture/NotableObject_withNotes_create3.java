package org.incode.examples.note.demo.usage.fixture;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;
import org.incode.examples.note.demo.usage.dom.demolink.NotableLinkForNotableObject_addNote;
import org.incode.examples.note.demo.shared.demo.dom.NotableObjectMenu;
import org.incode.example.note.dom.impl.note.T_addNote;

public class NotableObject_withNotes_create3 extends FixtureScript {


    @Override
    protected void execute(final ExecutionContext executionContext) {

        final LocalDate now = clockService.now();

        final NotableObject foo = create("Foo", executionContext);
        wrap(mixinAddNote(foo)).$$("Note A", now, "BLUE");
        wrap(mixinAddNote(foo)).$$("Note B", now.plusDays(1), "GREEN");
        wrap(mixinAddNote(foo)).$$("Note C", now.plusDays(2), "RED");

        final NotableObject bar = create("Bar", executionContext);
        wrap(mixinAddNote(bar)).$$("Note #1", null, null);
        wrap(mixinAddNote(bar)).$$("Note #2", now.plusDays(-1),
                "RED");

        final NotableObject baz = create("Baz", executionContext);
        wrap(mixinAddNote(baz)).$$("Another note", now.plusDays(1), "RED");
    }

    private NotableObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(noteDemoObjectMenu).createDemoObject(name));
    }


    T_addNote mixinAddNote(final Object notable) {
        return mixin(NotableLinkForNotableObject_addNote.class, notable);
    }

    @javax.inject.Inject
    NotableObjectMenu noteDemoObjectMenu;

    @javax.inject.Inject
    ClockService clockService;


}
