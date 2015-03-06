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
package domainapp.dom.modules.party;

import domainapp.dom.modules.comms.CommunicationChannelOwner;
import domainapp.dom.modules.comms.CommunicationChannelOwnerLink;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import com.google.common.eventbus.Subscribe;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.EventBusService;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommunicationChannelOwnerLinkForParties {

    //region > postConstruct, preDestroy

    @Programmatic
    @PostConstruct
    public void postConstruct() {
        eventBusService.register(this);
    }

    @Programmatic
    @PreDestroy
    public void preDestroy() {
        eventBusService.unregister(this);
    }
    //endregion


    @Programmatic
    @Subscribe
    public void on(final CommunicationChannelOwnerLink.PersistingEvent ev) {
        ev.addRunnable(new Runnable() {
            @Override
            public void run() {
                final CommunicationChannelOwner to = ev.getTo();
                if(to instanceof Party) {
                    final CommunicationChannelOwnerLink link = ev.getLink();
                    final Party party = (Party) to;
                    final CommunicationChannelOwnerLinkForParty ccolfp =
                            container.newTransientInstance(CommunicationChannelOwnerLinkForParty.class);
                    ccolfp.setLink(link);
                    ccolfp.setParty(party);
                }

            }
        });
    }


    //region > injected services
    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private EventBusService eventBusService;
    //endregion

}
