package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Dick;

public class UserRole_create_Dick_exampleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Dick_exampleRegularRole() {
        super(ApplicationUser_create_Dick.USER_NAME, RoleAndPermissions_create_exampleRegularRole.ROLE_NAME);
    }

}
