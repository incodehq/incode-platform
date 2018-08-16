package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.userrole;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.users.ApplicationUser_create_Bob;

public class UserRole_create_Bob_isisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bob_isisSecurityAdminRole() {
        super(ApplicationUser_create_Bob.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
