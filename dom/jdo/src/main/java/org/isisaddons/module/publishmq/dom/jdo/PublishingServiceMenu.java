/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.isisaddons.module.publishmq.dom.jdo;

import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

import org.isisaddons.module.publishmq.PublishMqModule;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "40"
)
public class PublishingServiceMenu extends AbstractService {

    public static abstract class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<PublishingServiceMenu> {
    }



    public static class FindPublishedEventsByTransactionIdDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = FindPublishedEventsByTransactionIdDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-crosshairs"
    )
    @MemberOrder(sequence="20")
    public List<PublishedEvent> findPublishedEventsByTransactionId(UUID transactionId) {
        return publishedEventRepository.findByTransactionId(transactionId);
    }



    public static class FindPublishedEventsDomainEvent extends ActionDomainEvent {
    }

    @Action(
            domainEvent = FindPublishedEventsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-search"
    )
    @MemberOrder(sequence="30")
    public List<PublishedEvent> findPublishedEvents(
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="From")
            final LocalDate from,
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="To")
            final LocalDate to) {
        return publishedEventRepository.findByFromAndTo(from, to);
    }

    public LocalDate default0FindPublishedEvents() {
        return clockService.now().minusDays(7);
    }
    public LocalDate default1FindPublishedEvents() {
        return clockService.now();
    }



    @javax.inject.Inject
    private PublishedEventRepository publishedEventRepository;

    @javax.inject.Inject
    private ClockService clockService;

}

