package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.roles.ExampleRegularRoleAndPermissions;
import org.isisaddons.module.security.fixture.scripts.users.DickUser;

public class DickUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public DickUser_Has_ExampleRegularRole() {
        super(DickUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
