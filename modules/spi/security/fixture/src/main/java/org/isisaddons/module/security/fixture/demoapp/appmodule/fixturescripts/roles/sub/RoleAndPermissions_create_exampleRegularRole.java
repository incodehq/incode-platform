package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

public class RoleAndPermissions_create_exampleRegularRole extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-regular-role";

    public RoleAndPermissions_create_exampleRegularRole() {
        super(ROLE_NAME, "Read/write access to example dom");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                NonTenantedEntity.class.getPackage().getName(),
                TenantedEntity.class.getPackage().getName()
                );
    }

}
