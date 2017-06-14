package org.isisaddons.module.security.fixture.scripts.userrole;

import org.isisaddons.module.security.fixture.scripts.roles.ExampleHideNonTenantedEntityDescriptionRoleAndPermissions;
import org.isisaddons.module.security.fixture.scripts.users.BobUser;

public class BobUser_Has_ExampleHideEntityDescriptionRole extends AbstractUserRoleFixtureScript {
    public BobUser_Has_ExampleHideEntityDescriptionRole() {
        super(BobUser.USER_NAME, ExampleHideNonTenantedEntityDescriptionRoleAndPermissions.ROLE_NAME);
    }
}
