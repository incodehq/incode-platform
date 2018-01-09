package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Sven;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

public class UserRole_create_SvenUser_IsisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_SvenUser_IsisSecurityAdminRole() {
        super(ApplicationUser_create_Sven.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
