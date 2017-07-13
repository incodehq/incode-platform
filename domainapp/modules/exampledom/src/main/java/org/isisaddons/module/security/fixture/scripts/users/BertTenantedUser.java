package org.isisaddons.module.security.fixture.scripts.users;

import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.fixture.scripts.tenancy.ItalyTenancy;

public class BertTenantedUser extends AbstractUserFixtureScript {

    public static final String USER_NAME = "bert";

    @Override
    protected void execute(ExecutionContext executionContext) {
        final ApplicationUser applicationUser = create(USER_NAME, AccountType.LOCAL, ItalyTenancy.TENANCY_PATH, executionContext);
        applicationUser.updateName("Tenant", "Bertrand", "Bert");
        applicationUser.updatePassword("pass");
        applicationUser.unlock();
    }

}
