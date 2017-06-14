package org.isisaddons.module.security.fixture.scripts.users;

import org.isisaddons.module.security.dom.user.AccountType;

/**
 * Intended to be assigned to roles ({@link org.isisaddons.module.security.fixture.scripts.roles.ExampleGuestRoleAndPermissions} and
 * {@link org.isisaddons.module.security.fixture.scripts.roles.ExampleNoGuestRoleAndPremissions}) that conflict.
 */
public class ConflictedUser extends AbstractUserFixtureScript {

    public static final String USER_NAME = "conflicted";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(USER_NAME, AccountType.LOCAL, null, executionContext);
    }

}
