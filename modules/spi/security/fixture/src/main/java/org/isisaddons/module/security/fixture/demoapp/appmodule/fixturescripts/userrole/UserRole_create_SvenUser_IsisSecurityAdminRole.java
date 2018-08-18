package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

public class UserRole_create_SvenUser_IsisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_SvenUser_IsisSecurityAdminRole() {
        super(ApplicationUser_create_Sven.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
