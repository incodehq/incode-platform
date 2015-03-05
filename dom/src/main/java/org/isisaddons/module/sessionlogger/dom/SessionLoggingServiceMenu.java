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

import java.util.List;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;
import org.joda.time.LocalDate;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * This service exposes a &lt;Sessions&gt; menu to the secondary menu bar for searching for sessions.
 *
 * <p>
 * Because this service influences the UI, it must be explicitly registered as a service
 * (eg using <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        named = "Activity",
        menuOrder = "10"
)
public class SessionLoggingServiceMenu extends AbstractService {

    public static abstract class ActionDomainEvent extends SessionLoggerModule.ActionDomainEvent<SessionLoggingServiceMenu> {
        public ActionDomainEvent(final SessionLoggingServiceMenu source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class ListAllActiveDomainEvent extends ActionDomainEvent {
        public ListAllActiveDomainEvent(final SessionLoggingServiceMenu source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = ListAllActiveDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            cssClassFa = "fa-bolt"
    )
    @MemberOrder(sequence = "10")
    public List<SessionLogEntry> activeSessions() {
        return sessionLogEntryRepository.listAllActiveSessions();
    }

    // //////////////////////////////////////

    public static class FindSessionsDomainEvent extends ActionDomainEvent {
        public FindSessionsDomainEvent(final SessionLoggingServiceMenu source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = FindSessionsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-search"
    )
    @MemberOrder(sequence = "20")
    public List<SessionLogEntry> findSessions(
            @Parameter(
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(named = "User")
            final String username,
            @Parameter(
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(named = "From")
            final LocalDate from,
            @Parameter(
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(named = "To")
            final LocalDate to) {

        if(username == null) {
            return sessionLogEntryRepository.findByFromAndTo(from, to);
        } else {
            return sessionLogEntryRepository.findByUserAndFromAndTo(username, from, to);
        }
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SessionLogEntryRepository sessionLogEntryRepository;

}
