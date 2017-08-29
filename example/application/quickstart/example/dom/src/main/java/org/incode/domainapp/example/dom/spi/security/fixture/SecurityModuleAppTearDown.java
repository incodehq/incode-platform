package org.incode.domainapp.example.dom.spi.security.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class SecurityModuleAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"isissecurity\".\"ApplicationPermission\"");
        isisJdoSupport.executeUpdate("delete from \"isissecurity\".\"ApplicationUserRoles\"");
        isisJdoSupport.executeUpdate("delete from \"isissecurity\".\"ApplicationRole\"");
        isisJdoSupport.executeUpdate("delete from \"isissecurity\".\"ApplicationUser\"");
        isisJdoSupport.executeUpdate("delete from \"isissecurity\".\"ApplicationTenancy\"");

        isisJdoSupport.executeUpdate("delete from \"exampleSpiSecurity\".\"NonTenantedEntity\"");
        isisJdoSupport.executeUpdate("delete from \"exampleSpiSecurity\".\"TenantedEntity\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
