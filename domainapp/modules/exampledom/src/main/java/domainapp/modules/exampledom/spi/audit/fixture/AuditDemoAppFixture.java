package domainapp.modules.exampledom.spi.audit.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import domainapp.modules.exampledom.spi.audit.dom.audited.SomeAuditedObject;
import domainapp.modules.exampledom.spi.audit.dom.audited.SomeAuditedObjects;

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
