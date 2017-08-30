#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.spi.audit.fixture.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class SomeAuditedObject_and_SomeNonAuditedObject_and_AuditEntry_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isisaudit${symbol_escape}".${symbol_escape}"AuditEntry${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleSpiAudit${symbol_escape}".${symbol_escape}"SomeAuditedObject${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleSpiAudit${symbol_escape}".${symbol_escape}"SomeNotAuditedObject${symbol_escape}"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
