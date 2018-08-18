package org.isisaddons.module.command.fixture.demoapp.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

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
