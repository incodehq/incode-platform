package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.users.BobUser;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;

public class BobUser_Has_IsisSecurityAdminRole extends AbstractUserRoleFixtureScript {
    public BobUser_Has_IsisSecurityAdminRole() {
        super(BobUser.USER_NAME, IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME);
    }
}
