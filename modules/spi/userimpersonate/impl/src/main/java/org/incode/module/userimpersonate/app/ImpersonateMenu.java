package org.incode.module.userimpersonate.app;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.services.message.MessageService;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.user.ApplicationUser;

import org.incode.module.userimpersonate.UserImpersonateModule;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "userimpersonate.ImpersonateMenu"
)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.TERTIARY,
        menuOrder = "30"
)
public class ImpersonateMenu {

    public static class ImpersonateDomainEvent
        extends UserImpersonateModule.ActionDomainEvent<ImpersonateMenu> {}

    @Action(
            domainEvent = ImpersonateDomainEvent.class,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @MemberOrder(sequence = "1")
    public void impersonate(
            final ApplicationUser applicationUser,
            @Nullable
            final List<ApplicationRole> applicationRoleList) {

        impersonationService.impersonate(applicationUser, applicationRoleList);

        messageService.informUser("Now impersonating " + applicationUser.getName());
    }
    public List<ApplicationRole> default1Impersonate() {
        return Lists.newArrayList(meService.me().getRoles());
    }

    public boolean hideImpersonate() {
        return impersonationService.hideImpersonate();
    }




    public static class StopImpersonatingDomainEvent
        extends UserImpersonateModule.ActionDomainEvent<ImpersonateMenu> {}

    @Action(
            domainEvent = StopImpersonatingDomainEvent.class,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @MemberOrder(sequence = "2")
    public void stopImpersonating() {

        impersonationService.stopImpersonating();

        messageService.informUser("No longer impersonating another user");

    }
    public boolean hideStopImpersonating() {
        return impersonationService.hideStopImpersonating();
    }




    @Inject
    ImpersonationService impersonationService;

    @Inject
    MessageService messageService;

    @Inject
    MeService meService;

}
