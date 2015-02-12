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
package org.isisaddons.module.command.dom;

import java.util.List;
import java.util.UUID;
import org.isisaddons.module.command.CommandModule;
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
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "20"
)
public class CommandServiceMenu extends AbstractService {

    public static abstract class PropertyDomainEvent<T> extends CommandModule.PropertyDomainEvent<CommandServiceMenu, T> {
        public PropertyDomainEvent(final CommandServiceMenu source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final CommandServiceMenu source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends CommandModule.CollectionDomainEvent<CommandServiceMenu, T> {
        public CollectionDomainEvent(final CommandServiceMenu source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final CommandServiceMenu source, final Identifier identifier, final org.apache.isis.applib.services.eventbus.CollectionDomainEvent.Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandServiceMenu> {
        public ActionDomainEvent(final CommandServiceMenu source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final CommandServiceMenu source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final CommandServiceMenu source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class CommandsCurrentlyRunningDomainEvent extends ActionDomainEvent {
        public CommandsCurrentlyRunningDomainEvent(final CommandServiceMenu source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = CommandsCurrentlyRunningDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            cssClassFa = "fa-bolt"
    )
    @MemberOrder(sequence="10")
    public List<CommandJdo> activeCommands() {
        return commandServiceRepository.findCurrent();
    }
    public boolean hideActiveCommands() {
        return commandServiceRepository == null;
    }

    // //////////////////////////////////////

    public static class FindCommandsDomainEvent extends ActionDomainEvent {
        public FindCommandsDomainEvent(final CommandServiceMenu source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = FindCommandsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-filter"
    )
    @MemberOrder(sequence="20")
    public List<CommandJdo> findCommands(
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="From")
            final LocalDate from,
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="To")
            final LocalDate to) {
        return commandServiceRepository.findByFromAndTo(from, to);
    }
    public boolean hideFindCommands() {
        return commandServiceRepository == null;
    }
    public LocalDate default0FindCommands() {
        return clockService.now().minusDays(7);
    }
    public LocalDate default1FindCommands() {
        return clockService.now();
    }

    // //////////////////////////////////////

    public static class FindCommandByIdDomainEvent extends ActionDomainEvent {
        public FindCommandByIdDomainEvent(final CommandServiceMenu source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = FindCommandByIdDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-search"
    )
    @MemberOrder(sequence="30")
    public CommandJdo findCommandById(
            @ParameterLayout(named="Transaction Id")
            final UUID transactionId) {
        return commandServiceRepository.findByTransactionId(transactionId);
    }
    public boolean hideFindCommandById() {
        return commandServiceRepository == null;
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;

    @javax.inject.Inject
    private ClockService clockService;

}

