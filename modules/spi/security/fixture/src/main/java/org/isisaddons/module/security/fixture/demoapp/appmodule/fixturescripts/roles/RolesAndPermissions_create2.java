package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub.RoleAndPermissions_create_exampleGuest;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub.RoleAndPermissions_create_exampleRegularRole;

public class RolesAndPermissions_create2 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleGuest());
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleRegularRole());
    }

}
