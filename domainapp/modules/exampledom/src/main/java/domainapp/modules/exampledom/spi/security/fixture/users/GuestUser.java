package domainapp.modules.exampledom.spi.security.fixture.users;

import org.isisaddons.module.security.dom.user.AccountType;

public class GuestUser extends AbstractUserFixtureScript {

    public static final String USER_NAME = "guest";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(USER_NAME, AccountType.LOCAL, null, executionContext);
    }

}
