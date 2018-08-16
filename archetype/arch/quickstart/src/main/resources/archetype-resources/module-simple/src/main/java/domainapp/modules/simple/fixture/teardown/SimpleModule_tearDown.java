#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package domainapp.modules.simple.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class SimpleModule_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"simple${symbol_escape}".${symbol_escape}"SimpleObject${symbol_escape}"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
