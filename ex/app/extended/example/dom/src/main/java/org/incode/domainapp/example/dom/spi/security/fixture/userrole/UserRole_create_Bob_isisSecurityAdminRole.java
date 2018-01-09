package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Bob;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

public class UserRole_create_Bob_isisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bob_isisSecurityAdminRole() {
        super(ApplicationUser_create_Bob.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
