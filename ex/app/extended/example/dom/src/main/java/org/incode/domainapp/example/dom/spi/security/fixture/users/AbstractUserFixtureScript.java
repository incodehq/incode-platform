package org.incode.domainapp.example.dom.spi.security.fixture.users;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Password;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserRepository;
import org.incode.domainapp.example.dom.spi.security.fixture.Util;

public abstract class AbstractUserFixtureScript extends FixtureScript {

    private ApplicationRole initialRole;

    /**
     * The initial role for the user to have, if any.
     *
     * <p>
     *     Defaults to <code>null</code>, meaning none.
     * </p>
     */
    public ApplicationRole getInitialRole() {
        return initialRole;
    }
    public void setInitialRole(ApplicationRole initialRole) {
        this.initialRole = initialRole;
    }

    private Boolean enabled;

    /**
     * Whether the user should be enabled or not.
     *
     * <p>
     *     Defaults to <code>null</code>, which is interpreted as disabled.
     * </p>
     */
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    private String password;

    /**
     * The password to set up for a {@link org.isisaddons.module.security.dom.user.AccountType#LOCAL local} user only.
     * Is ignored if setting up a delegate user.
     *
     * <p>
     *     Defaults to '12345678a'
     * </p>
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected ApplicationUser create(
            final String name,
            final AccountType accountType,
            final String tenancyPath,
            final ExecutionContext executionContext) {

        return create(name, null, accountType, tenancyPath, executionContext);
    }

    protected ApplicationUser create(
        final String name,
        final String emailAddress,
        final AccountType accountType,
        final String tenancyPath,
        final ExecutionContext executionContext) {

        final ApplicationUser applicationUser;
        if(accountType == AccountType.DELEGATED) {
            applicationUser = applicationUserRepository.newDelegateUser(name, null, null);
        } else {
            final String passwordStr = Util.coalesce(executionContext.getParameter("password"), getPassword(), "12345678a");
            final Password password = new Password(passwordStr);
            applicationUser = applicationUserRepository.newLocalUser(name, password, password, null, null, emailAddress);
        }

        applicationUser.setAtPath(tenancyPath);

        executionContext.addResult(this, name, applicationUser);
        return applicationUser;
    }

    @javax.inject.Inject
    private ApplicationUserRepository applicationUserRepository;
}
