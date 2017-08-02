package org.incode.domainapp.example.dom.spi.security.fixture.userrole;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.ExampleRegularRoleAndPermissions;
import org.incode.domainapp.example.dom.spi.security.fixture.users.DickUser;

public class DickUser_Has_ExampleRegularRole extends AbstractUserRoleFixtureScript {
    public DickUser_Has_ExampleRegularRole() {
        super(DickUser.USER_NAME, ExampleRegularRoleAndPermissions.ROLE_NAME);
    }

}
