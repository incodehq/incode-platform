package domainapp.modules.exampledom.spi.security.fixture.userrole;

import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleRegularRoleAndPermissions;
import domainapp.modules.exampledom.spi.security.fixture.users.DickUser;

public class DickUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public DickUser_Has_ExampleRegularRole() {
        super(DickUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
