package org.incode.domainapp.example.dom.spi.command.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.command.fixture.teardown.SomeCommandAnnotatedObjects_tearDown;

public class SomeCommandAnnotatedObject_recreate3 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new SomeCommandAnnotatedObjects_tearDown());
        executionContext.executeChild(this, new SomeCommandAnnotatedObject_create3());
    }


}
