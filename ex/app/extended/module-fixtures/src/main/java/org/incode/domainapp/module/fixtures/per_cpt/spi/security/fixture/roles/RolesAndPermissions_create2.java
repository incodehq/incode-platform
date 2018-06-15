package org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.roles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleGuest;
import org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleRegularRole;

public class RolesAndPermissions_create2 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleGuest());
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleRegularRole());
    }

}
