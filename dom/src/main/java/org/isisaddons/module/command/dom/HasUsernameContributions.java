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

import java.util.Collections;
import java.util.List;
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
import org.apache.isis.applib.services.HasUsername;


/**
 * This service contributes a <tt>recentCommandsByUser</tt> collection to any implementation of
 * {@link org.apache.isis.applib.services.HasUsername}.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class HasUsernameContributions extends AbstractFactoryAndRepository {

    public static abstract class ActionDomainEvent extends CommandModule.ActionDomainEvent<HasUsernameContributions> {
        public ActionDomainEvent(final HasUsernameContributions source, final Identifier identifier) {
            super(source, identifier);
        }
        public ActionDomainEvent(final HasUsernameContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
        public ActionDomainEvent(final HasUsernameContributions source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class RecentCommandsByUserDomainEvent extends HasUsernameContributions.ActionDomainEvent {
        public RecentCommandsByUserDomainEvent(final HasUsernameContributions source, final Identifier identifier, final Object... arguments) {
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
        if(hasUsername == null || hasUsername.getUsername() == null) {
            return Collections.emptyList();
        }
        return commandServiceRepository.findRecentByUser(hasUsername.getUsername());
    }
    public boolean hideRecentCommandsByUser(final HasUsername hasUsername) {
        return hasUsername == null || hasUsername.getUsername() == null;
    }

    // //////////////////////////////////////

    
    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;


}
