package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.users.ApplicationUser_create_Guest;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class UserRole_create_Guest_IsisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Guest_IsisSecurityModuleRegularRole() {
        super(ApplicationUser_create_Guest.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
