#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.note.fixture;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.note.dom.demolink.NotableLinkForDemoObject_addNote;
import org.incode.module.note.dom.impl.note.T_addNote;

public class DemoObject_withNotes_recreate3 extends FixtureScript {

    @javax.inject.Inject
    DemoObjectMenu noteDemoObjectMenu;
    @javax.inject.Inject
    ClockService clockService;

    T_addNote mixinAddNote(final Object notable) {
        return mixin(NotableLinkForDemoObject_addNote.class, notable);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new DemoModule_withNotes_tearDown());

        final LocalDate now = clockService.now();

        final DemoObject foo = create("Foo", executionContext);
        wrap(mixinAddNote(foo)).${symbol_dollar}${symbol_dollar}("Note A", now, "BLUE");
        wrap(mixinAddNote(foo)).${symbol_dollar}${symbol_dollar}("Note B", now.plusDays(1), "GREEN");
        wrap(mixinAddNote(foo)).${symbol_dollar}${symbol_dollar}("Note C", now.plusDays(2), "RED");

        final DemoObject bar = create("Bar", executionContext);
        wrap(mixinAddNote(bar)).${symbol_dollar}${symbol_dollar}("Note ${symbol_pound}1", null, null);
        wrap(mixinAddNote(bar)).${symbol_dollar}${symbol_dollar}("Note ${symbol_pound}2", now.plusDays(-1),
                "RED");

        final DemoObject baz = create("Baz", executionContext);
        wrap(mixinAddNote(baz)).${symbol_dollar}${symbol_dollar}("Another note", now.plusDays(1), "RED");
    }



    private DemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(noteDemoObjectMenu).createDemoObject(name));
    }


}
