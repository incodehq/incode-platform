package org.isisaddons.module.security.fixture.scripts.users;

import org.isisaddons.module.security.dom.user.AccountType;

public class DickUser extends AbstractUserFixtureScript {

    public static final String USER_NAME = "dick";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(USER_NAME, AccountType.LOCAL, null, executionContext);
    }

}
