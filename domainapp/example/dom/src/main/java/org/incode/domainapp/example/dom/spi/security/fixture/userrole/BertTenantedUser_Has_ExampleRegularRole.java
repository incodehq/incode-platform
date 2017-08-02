package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleRegularRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.users.BertTenantedUser;

public class BertTenantedUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public BertTenantedUser_Has_ExampleRegularRole() {
        super(BertTenantedUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
