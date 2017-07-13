package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.users.BertTenantedUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class BertTenantedUser_Has_IsisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public BertTenantedUser_Has_IsisSecurityModuleRegularRole() {
        super(BertTenantedUser.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
