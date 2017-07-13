package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.roles.ExampleRegularRoleAndPermissions;
import org.isisaddons.module.security.fixture.scripts.users.BillNonTenantedUser;

public class BillNonTenantedUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public BillNonTenantedUser_Has_ExampleRegularRole() {
        super(BillNonTenantedUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
