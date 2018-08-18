package org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts.sub;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.audit.dom.AuditEntry;

public class SomeAuditedObject_and_SomeNonAuditedObject_and_AuditEntry_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(AuditEntry.class);
        deleteFrom(SomeAuditedObject.class);
        deleteFrom(SomeNotAuditedObject.class);
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
