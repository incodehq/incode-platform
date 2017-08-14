package org.isisaddons.module.command.dom;

import java.util.UUID;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.command.Command;

import org.isisaddons.module.command.CommandModule;


/**
 * This mixin contributes a <tt>command</tt> action to any (non-command) implementation of
 * {@link org.apache.isis.applib.services.HasTransactionId}; that is: audit entries, and published events.  Thus, it
 * is possible to navigate from the effect back to the cause.
 */
@Mixin
public class HasTransactionId_command {


    public static class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<HasTransactionId_command> { }


    private final HasTransactionId hasTransactionId;
    public HasTransactionId_command(final HasTransactionId hasTransactionId) {
        this.hasTransactionId = hasTransactionId;
    }


    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="transactionId", sequence="1")
    public CommandJdo $$() {
        return findCommand();
    }
    /**
     * Hide if the contributee is a {@link Command}, because {@link Command}s already have a
     * {@link Command#getParent() parent} property.
     */
    public boolean hide$$() {
        return (hasTransactionId instanceof Command);
    }
    public String disable$$() {
        return findCommand() == null ? "No command found for transaction Id": null;
    }

    private CommandJdo findCommand() {
        final UUID transactionId = hasTransactionId.getTransactionId();
        return commandServiceRepository.findByTransactionId(transactionId);
    }


    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;


}
