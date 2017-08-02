package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleRegularRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.users.BillNonTenantedUser;

public class BillNonTenantedUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public BillNonTenantedUser_Has_ExampleRegularRole() {
        super(BillNonTenantedUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
