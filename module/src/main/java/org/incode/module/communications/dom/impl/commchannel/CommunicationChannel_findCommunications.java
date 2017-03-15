/*
 *  Copyright 2016 Dan Haywood
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
package org.incode.module.communications.dom.impl.commchannel;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

import org.isisaddons.module.command.dom.T_backgroundCommands;

import org.incode.module.communications.dom.impl.comms.Communication;
import org.incode.module.communications.dom.impl.comms.CommunicationRepository;

@Mixin(method = "act")
public class CommunicationChannel_findCommunications {

    public static final int MONTHS_PREVIOUS = 3;
    private final CommunicationChannel communicationChannel;

    public CommunicationChannel_findCommunications(final CommunicationChannel communicationChannel) {
        this.communicationChannel = communicationChannel;
    }

    public static class ActionDomainEvent extends T_backgroundCommands.ActionDomainEvent {}

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    public List<Communication> act(final LocalDate from, final LocalDate to) {

        final DateTime fromDateTime = toDateTime(from);
        final DateTime toDateTime = toDateTime(to).plusDays(1);

        return communicationRepository.findByCommunicationChannelAndQueuedOrSentBetween(this.communicationChannel, fromDateTime, toDateTime);
    }

    public LocalDate default0Act() {
        return clockService.now().minusMonths(MONTHS_PREVIOUS);
    }
    public LocalDate default1Act() {
        return clockService.now();
    }

    private static DateTime toDateTime(final LocalDate localDate) {
        return localDate.toDateTimeAtStartOfDay();
    }

    @Inject
    ClockService clockService;

    @Inject
    CommunicationRepository communicationRepository;


}
