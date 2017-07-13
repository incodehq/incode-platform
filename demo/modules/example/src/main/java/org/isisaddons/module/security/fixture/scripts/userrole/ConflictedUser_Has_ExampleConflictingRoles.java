package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.roles.ExampleGuestRoleAndPermissions;
import org.isisaddons.module.security.fixture.scripts.roles.ExampleNoGuestRoleAndPremissions;
import org.isisaddons.module.security.fixture.scripts.users.ConflictedUser;

public class ConflictedUser_Has_ExampleConflictingRoles extends AbstractUserRoleFixtureScript {
    public ConflictedUser_Has_ExampleConflictingRoles() {
        super(ConflictedUser.USER_NAME,
                ExampleGuestRoleAndPermissions.ROLE_NAME,
                ExampleNoGuestRoleAndPremissions.ROLE_NAME);
    }
}
