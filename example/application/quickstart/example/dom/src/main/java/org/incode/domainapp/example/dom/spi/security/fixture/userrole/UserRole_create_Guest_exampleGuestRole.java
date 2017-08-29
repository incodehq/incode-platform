package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleGuest;
import org.incode.domainapp.example.dom.spi.security.fixture.users.ApplicationUser_create_Guest;

public class UserRole_create_Guest_exampleGuestRole extends AbstractUserRoleFixtureScript {
    public UserRole_create_Guest_exampleGuestRole() {
        super(ApplicationUser_create_Guest.USER_NAME, RoleAndPermissions_create_exampleGuest.ROLE_NAME);
    }
}
