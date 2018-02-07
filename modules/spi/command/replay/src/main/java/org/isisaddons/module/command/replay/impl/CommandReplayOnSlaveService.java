package org.isisaddons.module.command.replay.impl;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isiscommand.CommandReplayOnSlaveService"
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "20.3"
)
public class CommandReplayOnSlaveService {

    public static abstract class PropertyDomainEvent<T>
            extends CommandModule.PropertyDomainEvent<CommandReplayOnSlaveService, T> { }

    public static abstract class CollectionDomainEvent<T>
            extends CommandModule.CollectionDomainEvent<CommandReplayOnSlaveService, T> { }

    public static abstract class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandReplayOnSlaveService> {
    }


    //region > findReplayHwm

    public static class FindReplayHwmOnSlaveDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = FindReplayHwmOnSlaveDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-bath"
    )
    @MemberOrder(sequence="60.1")
    public CommandJdo findReplayHwmOnSlave() {
        return commandServiceJdoRepository.findReplayHwm();
    }

    //endregion


    //region findReplayedOnSlave

    public static class FindReplayQueueOnSlaveDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = FindReplayQueueOnSlaveDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-bus"
    )
    @MemberOrder(sequence="60.3")
    public List<CommandJdo> findReplayedOnSlave() {
        return commandServiceJdoRepository.findReplayedOnSlave();
    }

    //endregion


    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceJdoRepository;


}

