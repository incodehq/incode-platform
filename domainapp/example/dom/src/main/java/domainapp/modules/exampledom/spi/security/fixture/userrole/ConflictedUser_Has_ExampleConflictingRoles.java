package domainapp.modules.exampledom.spi.security.fixture.userrole;

import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleGuestRoleAndPermissions;
import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleNoGuestRoleAndPremissions;
import domainapp.modules.exampledom.spi.security.fixture.users.ConflictedUser;

public class ConflictedUser_Has_ExampleConflictingRoles extends AbstractUserRoleFixtureScript {
    public ConflictedUser_Has_ExampleConflictingRoles() {
        super(ConflictedUser.USER_NAME,
                ExampleGuestRoleAndPermissions.ROLE_NAME,
                ExampleNoGuestRoleAndPremissions.ROLE_NAME);
    }
}
