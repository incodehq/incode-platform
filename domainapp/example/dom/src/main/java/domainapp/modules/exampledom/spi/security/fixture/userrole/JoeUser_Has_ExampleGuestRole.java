package domainapp.modules.exampledom.spi.security.fixture.userrole;

import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleGuestRoleAndPermissions;
import domainapp.modules.exampledom.spi.security.fixture.users.JoeUser;

public class JoeUser_Has_ExampleGuestRole extends AbstractUserRoleFixtureScript {
    public JoeUser_Has_ExampleGuestRole() {
        super(JoeUser.USER_NAME, ExampleGuestRoleAndPermissions.ROLE_NAME);
    }
}
