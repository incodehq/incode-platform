package org.isisaddons.module.togglz.glue.service.userprovider;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;
import org.togglz.servlet.util.HttpServletRequestHolder;

import org.isisaddons.module.togglz.glue.seed.TogglzModuleAdminRole;

public class UserProviderUsingServletPrincipal implements UserProvider {

    @Override
    public FeatureUser getCurrentUser() {
        final HttpServletRequest request = HttpServletRequestHolder.get();
        if (request == null) {
            throw new IllegalStateException(
                    "Could not get request from HttpServletRequestHolder. Did you configure the TogglzFilter correctly?");
        }

        final Principal  principal = request.getUserPrincipal();
        final String principalName = principal != null ? principal.getName() : null;
        if(principalName == null) {
            return null;
        }

        final boolean isAdmin = request.isUserInRole(TogglzModuleAdminRole.ROLE_NAME);
        return new SimpleFeatureUser(principalName, isAdmin);
    }

}
