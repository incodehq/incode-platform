package org.incode.domainapp.example.dom.spi.security.fixture.users;

import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;

public class ApplicationUser_create_Bill extends AbstractUserFixtureScript {

    public static final String USER_NAME = "bill";

    @Override
    protected void execute(ExecutionContext executionContext) {
        final ApplicationUser applicationUser = create(USER_NAME, AccountType.LOCAL, null, executionContext);
        applicationUser.updateName("Non-tenant", "William", "Bill");
        applicationUser.updatePassword("pass");
        applicationUser.unlock();
    }

}
