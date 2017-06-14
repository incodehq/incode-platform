package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.users.JoeUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class JoeUser_Has_IsisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public JoeUser_Has_IsisSecurityModuleRegularRole() {
        super(JoeUser.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
