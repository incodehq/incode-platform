package domainapp.modules.exampledom.spi.audit.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import domainapp.modules.exampledom.spi.audit.dom.nonaudited.SomeNotAuditedObject;
import domainapp.modules.exampledom.spi.audit.dom.nonaudited.SomeNotAuditedObjects;

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
