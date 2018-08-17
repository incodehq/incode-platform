package org.isisaddons.module.security.dom.user;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasUsername;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.isisaddons.module.security.SecurityModule;

@Mixin(method = "exec")
public class HasUsername_open extends AbstractFactoryAndRepository {

    private final HasUsername hasUsername;

    public HasUsername_open(final HasUsername hasUsername) {
        this.hasUsername = hasUsername;
    }


    public static class ActionDomainEvent extends SecurityModule.ActionDomainEvent<HasUsername_open> {}

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "User", sequence = "1") // associate with a 'User' property (if any)
    public ApplicationUser exec() {
        if (hasUsername == null || hasUsername.getUsername() == null) {
            return null;
        }
        return applicationUserRepository.findByUsername(hasUsername.getUsername());
    }
    public boolean hideExec() {
        return hasUsername instanceof ApplicationUser;
    }

    public TranslatableString disableExec() {
        if (hasUsername == null || hasUsername.getUsername() == null) {
            return TranslatableString.tr("No username");
        }
        return null;
    }

    @Inject
    private ApplicationUserRepository applicationUserRepository;

}
