package org.incode.domainapp.example.dom.spi.security.fixture.users;

import org.isisaddons.module.security.dom.user.AccountType;

import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPermissions_create_exampleGuest;
import org.incode.domainapp.example.dom.spi.security.fixture.roles.sub.RoleAndPremissions_create_exampleNoGuest;

/**
 * Intended to be assigned to roles ({@link RoleAndPermissions_create_exampleGuest} and
 * {@link RoleAndPremissions_create_exampleNoGuest}) that conflict.
 */
public class ApplicationUser_Conflicted extends AbstractUserFixtureScript {

    public static final String USER_NAME = "conflicted";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(USER_NAME, AccountType.LOCAL, null, executionContext);
    }

}
