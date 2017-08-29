package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleHideNonTenantedEntityDescription;
import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Bob;

public class UserRole_create_Bob_exampleHideEntityDescriptionRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bob_exampleHideEntityDescriptionRole() {
        super(ApplicationUser_create_Bob.USER_NAME, RoleAndPermissions_create_exampleHideNonTenantedEntityDescription.ROLE_NAME);
    }
}
