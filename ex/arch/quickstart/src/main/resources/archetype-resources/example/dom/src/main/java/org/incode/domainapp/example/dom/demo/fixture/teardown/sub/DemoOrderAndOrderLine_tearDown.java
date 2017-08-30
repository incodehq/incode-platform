#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.demo.fixture.teardown.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoOrderAndOrderLine_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDemo${symbol_escape}".${symbol_escape}"DemoOrderLine${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleDemo${symbol_escape}".${symbol_escape}"DemoOrder${symbol_escape}"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
