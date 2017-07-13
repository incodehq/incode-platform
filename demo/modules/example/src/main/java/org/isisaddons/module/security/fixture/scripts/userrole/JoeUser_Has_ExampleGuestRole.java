package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.roles.ExampleGuestRoleAndPermissions;
import org.isisaddons.module.security.fixture.scripts.users.JoeUser;

public class JoeUser_Has_ExampleGuestRole extends AbstractUserRoleFixtureScript {
    public JoeUser_Has_ExampleGuestRole() {
        super(JoeUser.USER_NAME, ExampleGuestRoleAndPermissions.ROLE_NAME);
    }
}
