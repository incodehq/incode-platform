package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub.RoleAndPermissions_create_exampleRegularRole;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.users.ApplicationUser_create_Dick;

public class UserRole_create_Dick_exampleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Dick_exampleRegularRole() {
        super(ApplicationUser_create_Dick.USER_NAME, RoleAndPermissions_create_exampleRegularRole.ROLE_NAME);
    }

}
