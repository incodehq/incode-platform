package org.incode.domainapp.example.dom.spi.security.fixture.users;

import org.isisaddons.module.security.dom.user.AccountType;

public class ApplicationUser_create_Joe extends AbstractUserFixtureScript {

    public static final String USER_NAME = "joe";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(USER_NAME, AccountType.LOCAL, null, executionContext);
    }

}
