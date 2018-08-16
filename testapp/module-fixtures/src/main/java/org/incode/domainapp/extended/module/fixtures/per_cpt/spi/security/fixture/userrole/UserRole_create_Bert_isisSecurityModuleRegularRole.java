package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.userrole;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.users.ApplicationUser_create_Bert_in_Italy;

public class UserRole_create_Bert_isisSecurityModuleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bert_isisSecurityModuleRegularRole() {
        super(ApplicationUser_create_Bert_in_Italy.USER_NAME, IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
