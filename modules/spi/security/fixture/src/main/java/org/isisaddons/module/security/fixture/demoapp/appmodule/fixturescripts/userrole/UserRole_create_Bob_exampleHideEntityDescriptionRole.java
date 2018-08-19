package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.userrole;

import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.roles.sub.RoleAndPermissions_create_exampleHideNonTenantedEntityDescription;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.users.ApplicationUser_create_Bob;

public class UserRole_create_Bob_exampleHideEntityDescriptionRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bob_exampleHideEntityDescriptionRole() {
        super(ApplicationUser_create_Bob.USER_NAME, RoleAndPermissions_create_exampleHideNonTenantedEntityDescription.ROLE_NAME);
    }
}
