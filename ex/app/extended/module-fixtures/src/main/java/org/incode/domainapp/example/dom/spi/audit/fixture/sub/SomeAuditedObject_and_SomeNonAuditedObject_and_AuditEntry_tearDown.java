package org.incode.domainapp.example.dom.spi.audit.fixture.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class SomeAuditedObject_and_SomeNonAuditedObject_and_AuditEntry_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"isisaudit\".\"AuditEntry\"");
        isisJdoSupport.executeUpdate("delete from \"exampleSpiAudit\".\"SomeAuditedObject\"");
        isisJdoSupport.executeUpdate("delete from \"exampleSpiAudit\".\"SomeNotAuditedObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
