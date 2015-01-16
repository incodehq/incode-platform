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
import org.joda.time.LocalDate;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.ParameterLayout;

/**
 * This service exposes a &lt;Sessions&gt; menu to the secondary menu bar for searching for sessions.
 *
 * <p>
 * Because this service influences the UI, it must be explicitly registered as a service
 * (eg using <tt>isis.properties</tt>).
 */
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        named = "Sessions"
)
public class SessionLoggingServiceMenu extends AbstractService {
    @ActionSemantics(ActionSemantics.Of.SAFE)
    @MemberOrder(sequence = "1")
    public List<SessionLogEntry> listAllActive() {
        return sessionLogEntryRepository.listAllActiveSessions();
    }

    @ActionSemantics(ActionSemantics.Of.SAFE)
    @MemberOrder(sequence = "2")
    public List<SessionLogEntry> find(
            @ParameterLayout(named = "Username") @Optional
            final String username,
            @ParameterLayout(named = "From") @Optional
            final LocalDate from,
            @ParameterLayout(named = "To") @Optional
            final LocalDate to) {

        if(username == null) {
            return sessionLogEntryRepository.findByFromAndTo(from, to);
        } else {
            return sessionLogEntryRepository.findByUsernameAndFromAndTo(username, from, to);
        }
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SessionLogEntryRepository sessionLogEntryRepository;

}
