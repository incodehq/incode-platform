package org.incode.domainapp.example.dom.spi.security.fixture.roles.sub;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

import org.incode.domainapp.example.dom.spi.security.fixture.SecurityModuleAppSetUp;

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
