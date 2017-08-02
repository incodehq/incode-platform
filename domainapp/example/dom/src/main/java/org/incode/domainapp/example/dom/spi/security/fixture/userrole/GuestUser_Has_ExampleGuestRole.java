package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleGuestRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.users.GuestUser;

public class GuestUser_Has_ExampleGuestRole extends AbstractUserRoleFixtureScript {
    public GuestUser_Has_ExampleGuestRole() {
        super(GuestUser.USER_NAME, ExampleGuestRoleAndPermissions.ROLE_NAME);
    }
}
