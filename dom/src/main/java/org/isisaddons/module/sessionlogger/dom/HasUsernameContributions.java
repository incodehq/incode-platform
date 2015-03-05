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
package org.isisaddons.module.sessionlogger.dom;

import java.util.Collections;
import java.util.List;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;
import org.apache.isis.applib.AbstractService;
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
 * This service contributes a <tt>recentSessions</tt> collection to any implementation of
 * {@link org.apache.isis.applib.services.HasUsername}.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class HasUsernameContributions extends AbstractService {

    public static abstract class ActionDomainEvent extends SessionLoggerModule.ActionDomainEvent<HasUsernameContributions> {
        public ActionDomainEvent(final HasUsernameContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class RecentSessionsForUserDomainEvent extends HasUsernameContributions.ActionDomainEvent {
        public RecentSessionsForUserDomainEvent(final HasUsernameContributions source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = RecentSessionsForUserDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "200.100")
    public List<SessionLogEntry> recentSessionsForUser(final HasUsername hasUsername) {
        if(hasUsername == null || hasUsername.getUsername() == null) {
            return Collections.emptyList();
        }
        return sessionLogEntryRepository.findRecentByUser(hasUsername.getUsername());
    }
    public boolean hideRecentSessionsForUser(final HasUsername hasUsername) {
        return hasUsername == null || hasUsername.getUsername() == null;
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SessionLogEntryRepository sessionLogEntryRepository;

}
