package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.users.DickUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class DickUser_Has_IsisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public DickUser_Has_IsisSecurityModuleRegularRole() {
        super(DickUser.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
