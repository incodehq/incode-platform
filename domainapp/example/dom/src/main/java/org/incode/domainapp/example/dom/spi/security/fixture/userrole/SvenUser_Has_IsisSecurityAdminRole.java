package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.users.SvenUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

public class SvenUser_Has_IsisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public SvenUser_Has_IsisSecurityAdminRole() {
        super(SvenUser.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
