package domainapp.modules.exampledom.spi.security.fixture.userrole;

import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleRegularRoleAndPermissions;
import domainapp.modules.exampledom.spi.security.fixture.users.BillNonTenantedUser;

public class BillNonTenantedUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public BillNonTenantedUser_Has_ExampleRegularRole() {
        super(BillNonTenantedUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
