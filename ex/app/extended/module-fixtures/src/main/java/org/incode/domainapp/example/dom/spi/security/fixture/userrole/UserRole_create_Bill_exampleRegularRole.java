package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleRegularRole;
import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Bill;

public class UserRole_create_Bill_exampleRegularRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Bill_exampleRegularRole() {
        super(ApplicationUser_create_Bill.USER_NAME, RoleAndPermissions_create_exampleRegularRole.ROLE_NAME);
    }

}
