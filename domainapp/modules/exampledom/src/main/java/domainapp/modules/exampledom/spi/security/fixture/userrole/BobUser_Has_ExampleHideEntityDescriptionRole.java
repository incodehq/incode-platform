package domainapp.modules.exampledom.spi.security.fixture.userrole;

import domainapp.modules.exampledom.spi.security.fixture.roles.ExampleHideNonTenantedEntityDescriptionRoleAndPermissions;
import domainapp.modules.exampledom.spi.security.fixture.users.BobUser;

public class BobUser_Has_ExampleHideEntityDescriptionRole extends AbstractUserRoleFixtureScript {
    public BobUser_Has_ExampleHideEntityDescriptionRole() {
        super(BobUser.USER_NAME, ExampleHideNonTenantedEntityDescriptionRoleAndPermissions.ROLE_NAME);
    }
}
