package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleHideNonTenantedEntityDescriptionRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.users.BobUser;

public class BobUser_Has_ExampleHideEntityDescriptionRole extends AbstractUserRoleFixtureScript {
    public BobUser_Has_ExampleHideEntityDescriptionRole() {
        super(BobUser.USER_NAME, ExampleHideNonTenantedEntityDescriptionRoleAndPermissions.ROLE_NAME);
    }
}
