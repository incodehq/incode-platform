package org.incode.domainapp.example.dom.spi.audit.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.spi.audit.dom.demo.SomeAuditedObject;
import org.incode.domainapp.example.dom.spi.audit.dom.demo.SomeAuditedObjects;

public class SomeAuditedObjectsFixture extends DiscoverableFixtureScript {

    public SomeAuditedObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

    @Override
    protected void execute(ExecutionContext executionContext) {

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private SomeAuditedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, someAuditedObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SomeAuditedObjects someAuditedObjects;

}
