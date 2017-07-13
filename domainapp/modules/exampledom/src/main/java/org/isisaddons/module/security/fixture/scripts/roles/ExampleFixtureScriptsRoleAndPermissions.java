package org.isisaddons.module.security.fixture.scripts.roles;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.fixture.scripts.SecurityModuleAppSetUp;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

public class ExampleFixtureScriptsRoleAndPermissions extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-fixture-scripts";

    public ExampleFixtureScriptsRoleAndPermissions() {
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
