package domainapp.modules.exampledom.spi.security.fixture.userrole;

import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleGuestRoleAndPermissions;
import domainapp.modules.exampledom.spi.security.fixture.users.GuestUser;

public class GuestUser_Has_ExampleGuestRole extends AbstractUserRoleFixtureScript {
    public GuestUser_Has_ExampleGuestRole() {
        super(GuestUser.USER_NAME, ExampleGuestRoleAndPermissions.ROLE_NAME);
    }
}
