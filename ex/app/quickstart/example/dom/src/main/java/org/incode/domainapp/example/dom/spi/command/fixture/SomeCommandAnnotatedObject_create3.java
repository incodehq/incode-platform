package org.incode.domainapp.example.dom.spi.command.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObject;
import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObjects;

public class SomeCommandAnnotatedObject_create3 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

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
