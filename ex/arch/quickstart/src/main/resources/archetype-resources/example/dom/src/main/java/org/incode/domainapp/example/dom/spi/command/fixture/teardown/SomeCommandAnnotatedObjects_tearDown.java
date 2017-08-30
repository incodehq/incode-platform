#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.spi.command.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class SomeCommandAnnotatedObjects_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleSpiCommand${symbol_escape}".${symbol_escape}"SomeCommandAnnotatedObject${symbol_escape}"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
