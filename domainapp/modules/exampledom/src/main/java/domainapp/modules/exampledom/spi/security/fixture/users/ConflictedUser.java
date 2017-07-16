package domainapp.modules.exampledom.spi.security.fixture.users;

import org.isisaddons.module.security.dom.user.AccountType;

/**
 * Intended to be assigned to roles ({@link domainapp.modules.exampledom.spi.security.fixture.roles.ExampleGuestRoleAndPermissions} and
 * {@link domainapp.modules.exampledom.spi.security.fixture.roles.ExampleNoGuestRoleAndPremissions}) that conflict.
 */
public class ConflictedUser extends AbstractUserFixtureScript {

    public static final String USER_NAME = "conflicted";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(USER_NAME, AccountType.LOCAL, null, executionContext);
    }

}
