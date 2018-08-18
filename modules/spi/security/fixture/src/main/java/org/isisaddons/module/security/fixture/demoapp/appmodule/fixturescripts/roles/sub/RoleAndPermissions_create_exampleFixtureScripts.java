package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

public class RoleAndPermissions_create_exampleFixtureScripts extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-fixture-scripts";

    public RoleAndPermissions_create_exampleFixtureScripts() {
        super(ROLE_NAME, "Execute the example fixture scripts");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                SecurityModuleAppSetUp.class.getPackage().getName());
    }

}
