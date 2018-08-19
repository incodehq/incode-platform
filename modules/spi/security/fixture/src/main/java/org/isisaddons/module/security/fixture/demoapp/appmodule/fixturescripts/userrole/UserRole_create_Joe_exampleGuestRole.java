package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub.RoleAndPermissions_create_exampleGuest;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.users.ApplicationUser_create_Joe;

public class UserRole_create_Joe_exampleGuestRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Joe_exampleGuestRole() {
        super(ApplicationUser_create_Joe.USER_NAME, RoleAndPermissions_create_exampleGuest.ROLE_NAME);
    }
}
