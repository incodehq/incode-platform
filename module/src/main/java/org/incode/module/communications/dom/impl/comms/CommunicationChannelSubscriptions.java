/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.EventBusService;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannel;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommunicationChannelSubscriptions {

    @Subscribe
    public void on(final CommunicationChannel.RemoveEvent ev) {
        CommunicationChannel sourceCommunicationChannel = ev.getSource();
        CommunicationChannel replacementCommunicationChannel = ev.getReplacement();

        switch (ev.getEventPhase()) {
        case VALIDATE:
            final List<Communication> communications = communicationRepository.findByCommunicationChannel(sourceCommunicationChannel);
            if (communications.size() > 0 && replacementCommunicationChannel == null) {
                ev.invalidate("Communication channel is being used in a communication: provide a replacement");
            }
            break;
        case EXECUTING:
            for (Communication comm : communicationRepository.findByCommunicationChannel(sourceCommunicationChannel)) {
                for(CommChannelRole commChannelRole : comm.getCorrespondents()){
                    if(commChannelRole.getChannel().equals(sourceCommunicationChannel)){
                        commChannelRole.setChannel(replacementCommunicationChannel);
                    }
                }
            }
            break;
        default:
            break;
        }
    }

    @Inject
    CommunicationRepository communicationRepository;

    @Programmatic
    @PostConstruct
    public void init(final Map<String, String> properties) {
        eventBusService.register(this);
    }

    @Programmatic
    @PreDestroy
    public void shutdown() {
        eventBusService.unregister(this);
    }

    @javax.inject.Inject
    private EventBusService eventBusService;


}
