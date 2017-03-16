/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
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
package org.incode.module.communications.dom.impl.comms;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import org.joda.time.DateTime;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannel;
import org.incode.module.communications.dom.impl.commchannel.EmailAddress;
import org.incode.module.communications.dom.impl.commchannel.PostalAddress;
import org.incode.module.communications.dom.spi.CurrentUserEmailAddressProvider;

@DomainService(
        repositoryFor = Communication.class,
        objectType = "incodeCommunications.CommunicationRepository",
        nature = NatureOfService.DOMAIN
)
public class CommunicationRepository  {

    public String iconName() {
        return Communication.class.getSimpleName();
    }


    @Programmatic
    public Communication createEmail(
            final String subject,
            final String atPath,
            final EmailAddress to,
            final String ccIfAny,
            final String cc2IfAny,
            final String cc3IfAny,
            final String bccIfAny,
            final String bcc2IfAny) {
        final DateTime queuedAt = clockService.nowAsDateTime();

        final Communication communication = Communication.newEmail(atPath, subject, queuedAt);
        serviceRegistry2.injectServicesInto(communication);

        communication.addCorrespondent(CommChannelRoleType.TO, to);
        communication.addCorrespondentIfAny(CommChannelRoleType.CC, ccIfAny);
        communication.addCorrespondentIfAny(CommChannelRoleType.CC, cc2IfAny);
        communication.addCorrespondentIfAny(CommChannelRoleType.CC, cc3IfAny);
        communication.addCorrespondentIfAny(CommChannelRoleType.BCC, bccIfAny);
        communication.addCorrespondentIfAny(CommChannelRoleType.BCC, bcc2IfAny);

        final String currentUserEmailAddress = currentUserEmailAddressProvider.currentUserEmailAddress();
        communication.addCorrespondentIfAny(CommChannelRoleType.PREPARED_BY, currentUserEmailAddress);

        repositoryService.persist(communication);

        return communication;
    }

    @Programmatic
    public Communication createPostal(
            final String subject,
            final String atPath,
            final PostalAddress to) {

        final Communication communication = Communication.newPostal(atPath, subject);

        serviceRegistry2.injectServicesInto(communication);

        communication.addCorrespondent(CommChannelRoleType.TO, to);

        repositoryService.persist(communication);
        return communication;
    }

    @Programmatic
    public List<Communication> findByCommunicationChannelAndQueuedOrSentBetween(
            final CommunicationChannel communicationChannel,
            final DateTime fromDateTime, final DateTime toDateTime) {
        final List<Communication> communications =
                Lists.newArrayList(
                    repositoryService.allMatches(
                        new QueryDefault<>(Communication.class,
                                "findByCommunicationChannelAndQueuedOrSentBetween",
                                "communicationChannel", communicationChannel,
                                "from", fromDateTime,
                                "to", toDateTime))
                );

        final Ordering<Communication> queuedAtElseSentAtDescending =
                Ordering.natural()
                        .nullsFirst()   // shouldn't matter, but will be nulls last after reversed
                        .onResultOf(queuedAtElseSentAt())
                        .reverse();
        communications.sort(queuedAtElseSentAtDescending);

        return communications;
    }

    private static Function<Communication, Comparable> queuedAtElseSentAt() {
        return new Function<Communication, Comparable>() {
            @Nullable @Override
            public Comparable apply(@Nullable final Communication comm) {
                if(comm == null) { return null; }
                final DateTime queuedAt = comm.getQueuedAt();
                return queuedAt != null ? queuedAt : comm.getSentAt();
            }
        };
    }

    @Inject
    CurrentUserEmailAddressProvider currentUserEmailAddressProvider;

    @Inject
    ClockService clockService;

    @Inject
    ServiceRegistry2 serviceRegistry2;

    @Inject
    RepositoryService repositoryService;


}
