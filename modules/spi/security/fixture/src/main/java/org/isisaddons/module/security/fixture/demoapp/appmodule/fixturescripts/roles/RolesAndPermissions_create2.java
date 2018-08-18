package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class RolesAndPermissions_create2 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleGuest());
        executionContext.executeChild(this, new RoleAndPermissions_create_exampleRegularRole());
    }

}
