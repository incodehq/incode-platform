package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.roles.ExampleGuestRoleAndPermissions;
import org.isisaddons.module.security.fixture.scripts.users.GuestUser;

public class GuestUser_Has_ExampleGuestRole extends AbstractUserRoleFixtureScript {
    public GuestUser_Has_ExampleGuestRole() {
        super(GuestUser.USER_NAME, ExampleGuestRoleAndPermissions.ROLE_NAME);
    }
}
