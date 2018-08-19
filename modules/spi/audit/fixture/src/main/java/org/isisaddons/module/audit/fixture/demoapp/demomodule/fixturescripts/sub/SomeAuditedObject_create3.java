package org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObject;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObjects;

public class SomeAuditedObject_create3 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    private SomeAuditedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.addResult(this, someAuditedObjects.createSomeAuditedObject(name));
    }

    @javax.inject.Inject
    SomeAuditedObjects someAuditedObjects;

}
