package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.users;

import org.isisaddons.module.security.dom.user.AccountType;

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
