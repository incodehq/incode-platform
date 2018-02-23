package org.incode.module.userimpersonate.app;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.user.ApplicationUser;

import org.incode.module.userimpersonate.impl.UserServiceWithImpersonation;

@DomainService(nature = NatureOfService.DOMAIN)
public class ImpersonationService {


    @Programmatic
    public void impersonate(
            final ApplicationUser applicationUser,
            final boolean useExplicitRoles,
            final Collection<ApplicationRole> applicationRoleList) {

        if(userServiceWithImpersonation.isImpersonating()) {
            stopImpersonating();
        }

        final Collection<ApplicationRole> applicationRoles =
                useExplicitRoles ? applicationRoleList : applicationUser.getRoles();

        final List<String> roleNames = asRoleNames(applicationRoles);

        userServiceWithImpersonation.setUser(applicationUser.getUsername(), roleNames);

    }

    private List<String> asRoleNames(final Collection<ApplicationRole> applicationRoleList) {
        if(applicationRoleList == null) {
            return Collections.emptyList();
        }
        return applicationRoleList.stream().
                                   map(ApplicationRole::getName).
                                   collect(Collectors.toList());
    }

    @Programmatic
    public boolean hideImpersonate() {
        return !userServiceWithImpersonation.isAvailable();
    }



    @Programmatic
    public void stopImpersonating() {
        userServiceWithImpersonation.reset();
    }

    @Programmatic
    public boolean hideStopImpersonating() {
        return !userServiceWithImpersonation.isAvailable() || !userServiceWithImpersonation.isImpersonating();
    }




    @Inject
    MeService meService;

    @Inject
    UserServiceWithImpersonation userServiceWithImpersonation;


}
