package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

public class UserRole_create_Bob_isisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bob_isisSecurityAdminRole() {
        super(ApplicationUser_create_Bob.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
