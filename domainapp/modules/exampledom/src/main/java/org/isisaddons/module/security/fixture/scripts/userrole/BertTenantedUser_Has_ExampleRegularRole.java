package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.roles.ExampleRegularRoleAndPermissions;
import org.isisaddons.module.security.fixture.scripts.users.BertTenantedUser;

public class BertTenantedUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public BertTenantedUser_Has_ExampleRegularRole() {
        super(BertTenantedUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
