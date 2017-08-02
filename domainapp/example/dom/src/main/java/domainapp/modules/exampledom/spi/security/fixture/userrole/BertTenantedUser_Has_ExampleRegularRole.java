package domainapp.modules.exampledom.spi.security.fixture.userrole;

import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleRegularRoleAndPermissions;
import domainapp.modules.exampledom.spi.security.fixture.users.BertTenantedUser;

public class BertTenantedUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public BertTenantedUser_Has_ExampleRegularRole() {
        super(BertTenantedUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
