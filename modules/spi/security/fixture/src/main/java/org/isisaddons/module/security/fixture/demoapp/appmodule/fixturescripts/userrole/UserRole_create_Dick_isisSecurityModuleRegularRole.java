package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class UserRole_create_Dick_isisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Dick_isisSecurityModuleRegularRole() {
        super(ApplicationUser_create_Dick.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
