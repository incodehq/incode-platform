package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.userrole;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.users.ApplicationUser_create_Sven;

public class UserRole_create_SvenUser_IsisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_SvenUser_IsisSecurityAdminRole() {
        super(ApplicationUser_create_Sven.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
