package org.isisaddons.module.audit.fixture.scripts;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.audit.fixture.dom.notaudited.SomeNotAuditedObject;
import org.isisaddons.module.audit.fixture.dom.notaudited.SomeNotAuditedObjects;

public class SomeNotAuditedObjectsFixture extends DiscoverableFixtureScript {

    public SomeNotAuditedObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private SomeNotAuditedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, someNotAuditedObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SomeNotAuditedObjects someNotAuditedObjects;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;


}
