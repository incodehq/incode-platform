package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleGuestRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.users.JoeUser;

public class JoeUser_Has_ExampleGuestRole extends AbstractUserRoleFixtureScript {
    public JoeUser_Has_ExampleGuestRole() {
        super(JoeUser.USER_NAME, ExampleGuestRoleAndPermissions.ROLE_NAME);
    }
}
