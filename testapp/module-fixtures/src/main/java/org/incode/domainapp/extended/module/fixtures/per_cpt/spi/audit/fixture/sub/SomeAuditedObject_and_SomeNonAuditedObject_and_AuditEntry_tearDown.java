package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture.sub;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.isisaddons.module.audit.dom.AuditEntry;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.audited.SomeAuditedObject;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.dom.demo.notaudited.SomeNotAuditedObject;

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
