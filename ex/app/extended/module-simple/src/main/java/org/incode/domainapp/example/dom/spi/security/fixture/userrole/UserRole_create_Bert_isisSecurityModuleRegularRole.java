package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Bert_in_Italy;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class UserRole_create_Bert_isisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bert_isisSecurityModuleRegularRole() {
        super(ApplicationUser_create_Bert_in_Italy.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
