package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.users.BillNonTenantedUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class BillNonTenantedUser_Has_IsisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public BillNonTenantedUser_Has_IsisSecurityModuleRegularRole() {
        super(BillNonTenantedUser.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
