package org.incode.domainapp.example.dom.spi.audit.fixture.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.audit.dom.demo.notaudited.SomeNotAuditedObject;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.notaudited.SomeNotAuditedObjects;

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
