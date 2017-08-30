package org.incode.domainapp.example.dom.spi.security.fixture.users;

import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_Italy;

public class ApplicationUser_create_Bert_in_Italy extends AbstractUserFixtureScript {

    public static final String USER_NAME = "bert";

    @Override
    protected void execute(ExecutionContext executionContext) {
        final ApplicationUser applicationUser = create(USER_NAME, AccountType.LOCAL, ApplicationTenancy_create_Italy.TENANCY_PATH, executionContext);
        applicationUser.updateName("Tenant", "Bertrand", "Bert");
        applicationUser.updatePassword("pass");
        applicationUser.unlock();
    }

}
