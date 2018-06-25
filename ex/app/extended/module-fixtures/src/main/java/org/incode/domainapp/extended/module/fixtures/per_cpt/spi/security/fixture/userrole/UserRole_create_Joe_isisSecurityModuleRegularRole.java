package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.userrole;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.users.ApplicationUser_create_Joe;

public class UserRole_create_Joe_isisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Joe_isisSecurityModuleRegularRole() {
        super(ApplicationUser_create_Joe.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
