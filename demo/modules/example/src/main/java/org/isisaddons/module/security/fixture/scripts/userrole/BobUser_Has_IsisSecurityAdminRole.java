package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.users.BobUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

public class BobUser_Has_IsisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public BobUser_Has_IsisSecurityAdminRole() {
        super(BobUser.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
