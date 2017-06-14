package org.isisaddons.module.security.fixture.scripts.users;

import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;

public class BillNonTenantedUser extends AbstractUserFixtureScript {

    public static final String USER_NAME = "bill";

    @Override
    protected void execute(ExecutionContext executionContext) {
        final ApplicationUser applicationUser = create(USER_NAME, AccountType.LOCAL, null, executionContext);
        applicationUser.updateName("Non-tenant", "William", "Bill");
        applicationUser.updatePassword("pass");
        applicationUser.unlock();
    }

}
