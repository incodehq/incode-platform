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

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasUsername;

import org.isisaddons.module.sessionlogger.SessionLoggerModule;

@Mixin
public class HasUsername_recentSessionsForUser extends AbstractService {


    public static class ActionDomainEvent extends SessionLoggerModule.ActionDomainEvent<HasUsername_recentSessionsForUser> {
    }


    private final HasUsername hasUsername;
    public HasUsername_recentSessionsForUser(final HasUsername hasUsername) {
        this.hasUsername = hasUsername;
    }



    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(name = "user", sequence = "1")
    public List<SessionLogEntry> $$() {
        if(hasUsername == null || hasUsername.getUsername() == null) {
            return Collections.emptyList();
        }
        return sessionLogEntryRepository.findRecentByUser(hasUsername.getUsername());
    }
    public boolean hide$$() {
        return hasUsername == null || hasUsername.getUsername() == null;
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SessionLogEntryRepository sessionLogEntryRepository;

}
