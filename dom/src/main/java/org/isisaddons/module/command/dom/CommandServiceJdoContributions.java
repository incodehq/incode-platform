/*
 *  Copyright 2013~2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.command.dom;

import java.util.List;
import java.util.UUID;
import org.isisaddons.module.command.CommandModule;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.HasUsername;
import org.apache.isis.applib.services.command.Command;


/**
 * This service contributes a <tt>command</tt> action to any (non-command) implementation of
 * {@link org.apache.isis.applib.services.HasTransactionId}; that is: audit entries, and published events.  Thus, it
 * is possible to navigate from the effect back to the cause.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CommandServiceJdoContributions extends AbstractFactoryAndRepository {

    public static abstract class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandServiceJdoContributions> {
        public ActionDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier) {
            super(source, identifier);
        }
        public ActionDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
        public ActionDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class CommandDomainEvent extends ActionDomainEvent {
        public CommandDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier) {
            super(source, identifier);
        }
        public CommandDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
        public CommandDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = CommandDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name="transactionId", sequence="1")
    public CommandJdo command(final HasTransactionId hasTransactionId) {
        return commandServiceRepository.findByTransactionId(hasTransactionId.getTransactionId());
    }
    /**
     * Hide if the contributee is a {@link Command}, because {@link Command}s already have a
     * {@link Command#getParent() parent} property.
     */
    public boolean hideCommand(final HasTransactionId hasTransactionId) {
        return (hasTransactionId instanceof Command);
    }
    public String disableCommand(final HasTransactionId hasTransactionId) {
        if(hasTransactionId == null) {
            return "No transaction Id";
        }
        final UUID transactionId = hasTransactionId.getTransactionId();
        final boolean command = commandServiceRepository.findByTransactionId(transactionId) == null;
        return command? "No command found for transaction Id": null;
    }

    // //////////////////////////////////////

    public static class RecentCommandsByUserDomainEvent extends ActionDomainEvent {
        public RecentCommandsByUserDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier) {
            super(source, identifier);
        }
        public RecentCommandsByUserDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
        public RecentCommandsByUserDomainEvent(final CommandServiceJdoContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = RecentCommandsByUserDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "200.100")
    public List<CommandJdo> recentCommandsByUser(final HasUsername hasUsername) {
        return commandServiceRepository.findRecentByUser(hasUsername.getUsername());
    }

    // //////////////////////////////////////

    
    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;


}
