package org.isisaddons.module.security.fixture.scripts.roles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class AllExampleRolesAndPermissions extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new ExampleGuestRoleAndPermissions());
        executionContext.executeChild(this, new ExampleRegularRoleAndPermissions());
    }

}
