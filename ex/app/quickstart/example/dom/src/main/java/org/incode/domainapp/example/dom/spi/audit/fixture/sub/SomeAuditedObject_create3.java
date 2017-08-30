package org.incode.domainapp.example.dom.spi.audit.fixture.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.audit.dom.demo.audited.SomeAuditedObject;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.audited.SomeAuditedObjects;

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
