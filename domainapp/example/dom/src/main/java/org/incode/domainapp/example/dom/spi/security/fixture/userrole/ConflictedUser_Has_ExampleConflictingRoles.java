package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleGuestRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleNoGuestRoleAndPremissions;
import org.incode.domainapp.example.dom.spi.security.fixture.users.ConflictedUser;

public class ConflictedUser_Has_ExampleConflictingRoles extends AbstractUserRoleFixtureScript {
    public ConflictedUser_Has_ExampleConflictingRoles() {
        super(ConflictedUser.USER_NAME,
                ExampleGuestRoleAndPermissions.ROLE_NAME,
                ExampleNoGuestRoleAndPremissions.ROLE_NAME);
    }
}
