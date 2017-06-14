package org.isisaddons.module.audit.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class AuditDemoAppTearDownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"isisaudit\".\"AuditEntry\"");
        isisJdoSupport.executeUpdate("delete from \"isisauditdemo\".\"SomeAuditedObject\"");
        isisJdoSupport.executeUpdate("delete from \"isisauditdemo\".\"SomeNotAuditedObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
