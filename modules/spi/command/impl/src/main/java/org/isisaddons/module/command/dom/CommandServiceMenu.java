package org.isisaddons.module.command.dom;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.joda.time.LocalDate;

import org.apache.isis.applib.ApplicationException;
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
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.cmd.v1.CommandsDto;

import org.isisaddons.module.command.CommandModule;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isiscommand.CommandServiceMenu"
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "20"
)
public class CommandServiceMenu {

    public static abstract class PropertyDomainEvent<T>
            extends CommandModule.PropertyDomainEvent<CommandServiceMenu, T> { }

    public static abstract class CollectionDomainEvent<T>
            extends CommandModule.CollectionDomainEvent<CommandServiceMenu, T> { }

    public static abstract class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandServiceMenu> {
    }


    //region > activeCommands

    @Deprecated
    public static class CommandsCurrentlyRunningDomainEvent extends ActionDomainEvent { }
    public static class ActiveCommandsDomainEvent extends CommandsCurrentlyRunningDomainEvent { }

    @Action(
            domainEvent = ActiveCommandsDomainEvent.class,
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

    //endregion

    //region > findCommands

    public static class FindCommandsDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = FindCommandsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-search"
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

    //endregion

    //region > findCommandById

    public static class FindCommandByIdDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = FindCommandByIdDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-crosshairs"
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

    //endregion


    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceRepository;

    @javax.inject.Inject
    ClockService clockService;

}

