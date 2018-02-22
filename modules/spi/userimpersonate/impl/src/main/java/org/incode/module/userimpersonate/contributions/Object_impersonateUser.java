package org.incode.module.userimpersonate.contributions;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandPersistence;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.role.ApplicationRole;
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
            @Nullable
            final List<ApplicationRole> applicationRoleList) {

        impersonationService.impersonate(applicationUser, applicationRoleList);
        return object;
    }
    public List<ApplicationRole> default1Act() {
        return Lists.newArrayList(meService.me().getRoles());
    }
    public boolean hideAct() {
        return impersonationService.hideImpersonate();
    }


    @Inject
    MeService meService;
    @Inject
    ImpersonationService impersonationService;

}
