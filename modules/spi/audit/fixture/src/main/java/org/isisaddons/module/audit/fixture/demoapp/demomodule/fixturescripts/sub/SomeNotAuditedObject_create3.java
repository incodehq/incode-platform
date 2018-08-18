package org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SomeNotAuditedObject_create3 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    private SomeNotAuditedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.addResult(this, someNotAuditedObjects.createSomeNotAuditedObject(name));
    }

    @javax.inject.Inject
    SomeNotAuditedObjects someNotAuditedObjects;


}
