package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Joe;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class UserRole_create_Joe_isisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Joe_isisSecurityModuleRegularRole() {
        super(ApplicationUser_create_Joe.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
