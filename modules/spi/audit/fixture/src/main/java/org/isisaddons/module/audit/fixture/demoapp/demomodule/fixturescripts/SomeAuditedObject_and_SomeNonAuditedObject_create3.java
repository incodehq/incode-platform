package org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObject;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObjects;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts.sub.SomeAuditedObject_create3;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts.sub.SomeNotAuditedObject_create3;

public class SomeAuditedObject_and_SomeNonAuditedObject_create3 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new SomeAuditedObject_create3());
        executionContext.executeChild(this, new SomeNotAuditedObject_create3());

    }

    // //////////////////////////////////////

    private SomeAuditedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, someAuditedObjects.createSomeAuditedObject(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SomeAuditedObjects someAuditedObjects;

}
