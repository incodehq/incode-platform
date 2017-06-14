package org.isisaddons.module.audit.fixture.scripts;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObject;
import org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObjects;

public class AuditDemoAppFixture extends DiscoverableFixtureScript {

    public AuditDemoAppFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new AuditDemoAppTearDownFixture());

        executionContext.executeChild(this, new SomeAuditedObjectsFixture());
        executionContext.executeChild(this, new SomeNotAuditedObjectsFixture());

    }

    // //////////////////////////////////////

    private SomeAuditedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, someAuditedObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SomeAuditedObjects someAuditedObjects;

}
