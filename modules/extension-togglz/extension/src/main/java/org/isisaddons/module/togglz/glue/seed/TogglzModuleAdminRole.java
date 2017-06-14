package org.isisaddons.module.togglz.glue.seed;

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

public class TogglzModuleAdminRole extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "isis-module-togglz-admin";

    public TogglzModuleAdminRole() {
        super(ROLE_NAME, "Admin access to togglz module");
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                "org.isisaddons.module.togglz.glue");
    }

}
