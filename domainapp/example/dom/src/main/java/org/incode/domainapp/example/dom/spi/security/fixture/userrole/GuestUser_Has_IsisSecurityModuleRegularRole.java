package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.users.GuestUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class GuestUser_Has_IsisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public GuestUser_Has_IsisSecurityModuleRegularRole() {
        super(GuestUser.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
