#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.spi.security.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class SecurityModuleAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isissecurity${symbol_escape}".${symbol_escape}"ApplicationPermission${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isissecurity${symbol_escape}".${symbol_escape}"ApplicationUserRoles${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isissecurity${symbol_escape}".${symbol_escape}"ApplicationRole${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isissecurity${symbol_escape}".${symbol_escape}"ApplicationUser${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"isissecurity${symbol_escape}".${symbol_escape}"ApplicationTenancy${symbol_escape}"");

        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleSpiSecurity${symbol_escape}".${symbol_escape}"NonTenantedEntity${symbol_escape}"");
        isisJdoSupport.executeUpdate("delete from ${symbol_escape}"exampleSpiSecurity${symbol_escape}".${symbol_escape}"TenantedEntity${symbol_escape}"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
