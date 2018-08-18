package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

public class RoleAndPermissions_create_exampleGuest extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "example-guest";

    public RoleAndPermissions_create_exampleGuest() {
        super(ROLE_NAME, "Read only access to example dom");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.VIEWING,
                NonTenantedEntity.class.getPackage().getName(),
                TenantedEntity.class.getPackage().getName()
                );
    }

}
