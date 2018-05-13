package org.incode.module.userimpersonate.contributions;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandPersistence;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoleRepository;
import org.isisaddons.module.security.dom.user.ApplicationUser;

import org.incode.module.userimpersonate.UserImpersonateModule;
import org.incode.module.userimpersonate.app.ImpersonationService;

@Mixin(method = "act")
public class Object_impersonateUser {

    private final Object object;

    public Object_impersonateUser(final Object object) {
        this.object = object;
    }

    public static class ActionDomainEvent
            extends UserImpersonateModule.ActionDomainEvent<Object_impersonateUser> {}

    @Action(
            domainEvent = ActionDomainEvent.class,
            restrictTo = RestrictTo.PROTOTYPING,
            commandPersistence = CommandPersistence.NOT_PERSISTED
    )
    @MemberOrder(sequence = "90.1")
    public Object act(
            final ApplicationUser applicationUser,
            @ParameterLayout(describedAs = "If set, then the roles specified below are used.  Otherwise uses roles of the user.")
            final boolean useExplicitRolesBelow,
            @ParameterLayout(describedAs = "Only used if 'useExplicitRolesBelow' is set, otherwise is ignored.")
            @Nullable
            final List<ApplicationRole> applicationRoleList) {

        impersonationService.impersonate(applicationUser, useExplicitRolesBelow, applicationRoleList);
        return object;
    }
    public boolean default1Act() {
        return false;
    }
    public List<ApplicationRole> default2Act() {
        return applicationRoleRepository.allRoles();
    }
    public boolean hideAct() {
        return impersonationService.hideImpersonate();
    }


    @Inject
    ImpersonationService impersonationService;
    @Inject
    ApplicationRoleRepository applicationRoleRepository;

}
