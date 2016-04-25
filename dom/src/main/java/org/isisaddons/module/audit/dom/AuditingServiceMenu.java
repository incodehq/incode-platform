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
package org.isisaddons.module.audit.dom;

import java.util.List;

import org.joda.time.LocalDate;

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

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "30"
)
public class AuditingServiceMenu {

    //region > domain events
    public static abstract class PropertyDomainEvent<T>
            extends org.apache.isis.applib.services.eventbus.PropertyDomainEvent<AuditingServiceMenu, T> {
    }

    public static abstract class CollectionDomainEvent<T>
            extends org.apache.isis.applib.services.eventbus.CollectionDomainEvent<AuditingServiceMenu, T> {
    }

    public static abstract class ActionDomainEvent
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<AuditingServiceMenu> {
    }
    //endregion

    //region > findAuditEntries (action)
    public static class FindAuditEntriesDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = FindAuditEntriesDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-search"
    )
    @MemberOrder(sequence="10")
    public List<AuditEntry> findAuditEntries(
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="From")
            final LocalDate from,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="To")
            final LocalDate to) {
        return auditingServiceRepository.findByFromAndTo(from, to);
    }
    public boolean hideFindAuditEntries() {
        return auditingServiceRepository == null;
    }
    public LocalDate default0FindAuditEntries() {
        return clockService.now().minusDays(7);
    }
    public LocalDate default1FindAuditEntries() {
        return clockService.now();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;
    
    @javax.inject.Inject
    private ClockService clockService;
    //endregion

}

