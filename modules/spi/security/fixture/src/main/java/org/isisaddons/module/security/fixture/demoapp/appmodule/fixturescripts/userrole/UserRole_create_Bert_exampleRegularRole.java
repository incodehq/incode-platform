package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub.RoleAndPermissions_create_exampleRegularRole;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.users.ApplicationUser_create_Bert_in_Italy;

public class UserRole_create_Bert_exampleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bert_exampleRegularRole() {
        super(ApplicationUser_create_Bert_in_Italy.USER_NAME, RoleAndPermissions_create_exampleRegularRole.ROLE_NAME);
    }

}
