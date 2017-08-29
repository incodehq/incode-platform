package org.incode.domainapp.example.dom.spi.security.fixture.users;

import org.isisaddons.module.security.dom.user.AccountType;

public class ApplicationUser_create_Sven extends AbstractUserFixtureScript {

    public static final String USER_NAME = "sven";
    public static final String EMAIL = "sven@example.com";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(USER_NAME, EMAIL, AccountType.LOCAL, null, executionContext);
    }

}
