package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.userrole;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.users.ApplicationUser_create_Bill;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class UserRole_create_Bill_IsisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bill_IsisSecurityModuleRegularRole() {
        super(ApplicationUser_create_Bill.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
