package org.incode.domainapp.example.dom.spi.command.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObject;
import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObjects;
import org.incode.domainapp.example.dom.spi.command.fixture.teardown.SomeCommandAnnotatedObjects_tearDown;

public class SomeCommandAnnotatedObject_recreate3 extends DiscoverableFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new SomeCommandAnnotatedObjects_tearDown());

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }


    private SomeCommandAnnotatedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, someCommandAnnotatedObjects.createSomeCommandAnnotatedObject(name));
    }

    @javax.inject.Inject
    SomeCommandAnnotatedObjects someCommandAnnotatedObjects;

}
