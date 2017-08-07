package org.incode.domainapp.example.dom.dom.note.fixture;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.note.dom.demolink.NotableLinkForDemoObject_addNote;
import org.incode.module.note.dom.impl.note.T_addNote;

public class NoteDemoObjectsFixture extends DiscoverableFixtureScript {

    //region > injected services
    @javax.inject.Inject
    DemoObjectMenu noteDemoObjectMenu;
    @javax.inject.Inject
    ClockService clockService;
    //endregion

    //region > constructor
    public NoteDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }
    //endregion

    //region > mixins
    T_addNote mixinAddNote(final Object notable) {
        return mixin(NotableLinkForDemoObject_addNote.class, notable);
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new NoteDemoObjectsTearDownFixture());

        final LocalDate now = clockService.now();

        final DemoObject foo = create("Foo", executionContext);
        wrap(mixinAddNote(foo)).$$("Note A", now, "BLUE");
        wrap(mixinAddNote(foo)).$$("Note B", now.plusDays(1), "GREEN");
        wrap(mixinAddNote(foo)).$$("Note C", now.plusDays(2), "RED");

        final DemoObject bar = create("Bar", executionContext);
        wrap(mixinAddNote(bar)).$$("Note #1", null, null);
        wrap(mixinAddNote(bar)).$$("Note #2", now.plusDays(-1),
                "RED");

        final DemoObject baz = create("Baz", executionContext);
        wrap(mixinAddNote(baz)).$$("Another note", now.plusDays(1), "RED");
    }



    private DemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(noteDemoObjectMenu).create(name));
    }


}