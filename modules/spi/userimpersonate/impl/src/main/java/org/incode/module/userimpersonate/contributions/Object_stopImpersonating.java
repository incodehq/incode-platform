package org.incode.module.userimpersonate.contributions;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandPersistence;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;

import org.incode.module.userimpersonate.UserImpersonateModule;
import org.incode.module.userimpersonate.app.ImpersonationService;

@Mixin(method = "act")
public class Object_stopImpersonating {

    private final Object object;

    public Object_stopImpersonating(final Object object) {
        this.object = object;
    }

    public static class ActionDomainEvent
            extends UserImpersonateModule.ActionDomainEvent<Object_stopImpersonating> {}

    @Action(
            domainEvent = ActionDomainEvent.class,
            restrictTo = RestrictTo.PROTOTYPING,
            commandPersistence = CommandPersistence.NOT_PERSISTED
    )
    @MemberOrder(sequence = "90.2")
    public Object act() {
        impersonationService.stopImpersonating();
        return object;
    }

    public boolean hideAct() {
        return impersonationService.hideStopImpersonating();
    }


    @Inject
    ImpersonationService impersonationService;


}
