#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.dom.settings.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ApplicationSetting_and_UserSetting_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isissettings${symbol_escape}".${symbol_escape}"UserSetting${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isissettings${symbol_escape}".${symbol_escape}"ApplicationSetting${symbol_escape}"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
