package org.isisaddons.module.security.userreg;

import java.util.Set;

import javax.inject.Inject;

import org.apache.isis.applib.services.userreg.UserDetails;
import org.apache.isis.applib.services.userreg.UserRegistrationService;
import org.apache.isis.applib.value.Password;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserRepository;

/**
 * An abstract implementation of {@link org.apache.isis.applib.services.userreg.UserRegistrationService}
 * with a single abstract method for the initial role of newly created local users
 */
public abstract class SecurityModuleAppUserRegistrationServiceAbstract implements UserRegistrationService {

    @Override
    public boolean usernameExists(final String username) {
        return applicationUserRepository.findByUsername(username) != null;
    }

    @Override
    public void registerUser(
        final UserDetails userDetails) {

        final Password password = new Password(userDetails.getPassword());
        final ApplicationRole initialRole = getInitialRole();
        final Boolean enabled = true;
        final String username = userDetails.getUsername();
        final String emailAddress = userDetails.getEmailAddress();
        final ApplicationUser applicationUser = applicationUserRepository.newLocalUser(username, password, password, initialRole, enabled, emailAddress);

        final Set<ApplicationRole> additionalRoles = getAdditionalInitialRoles();
        if(additionalRoles != null) {
            for (final ApplicationRole additionalRole : additionalRoles) {
                applicationUser.addRole(additionalRole);
            }
        }

    }

    @Override
    public boolean emailExists(final String emailAddress) {
        return applicationUserRepository.findByEmailAddress(emailAddress) != null;
    }

    @Override
    public boolean updatePasswordByEmail(final String emailAddress, final String password) {
        boolean passwordUpdated = false;
        final ApplicationUser user = applicationUserRepository.findByEmailAddress(emailAddress);
        if (user != null) {
            user.updatePassword(password);
            passwordUpdated = true;
        }
        return passwordUpdated;
    }

    /**
     * @return The role to use for newly created local users
     */
    protected abstract ApplicationRole getInitialRole();

    /**
     * @return Additional roles for newly created local users
     */
    protected abstract Set<ApplicationRole> getAdditionalInitialRoles();

    @Inject
    private ApplicationUserRepository applicationUserRepository;
}
