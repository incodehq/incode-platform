package org.isisaddons.module.command.replay.impl.mixins;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.core.metamodel.services.configinternal.ConfigurationServiceInternal;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.command.replay.impl.SlaveConfiguration;

@Mixin(method = "coll")
public class CommandJdo_replayQueue {


    public static class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandJdo_replayQueue> { }


    private final CommandJdo commandJdo;
    public CommandJdo_replayQueue(final CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }


    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(sequence = "100.100")
    public List<CommandJdo> coll() {
        return commandServiceJdoRepository.findReplayedOnSlave();
    }

    public boolean hideColl() {
        return !getSlaveConfig().isConfigured();
    }

    /**
     * lazily loaded
     */
    SlaveConfiguration slaveConfig;
    private SlaveConfiguration getSlaveConfig() {
        if (slaveConfig == null){
            slaveConfig = new SlaveConfiguration(configurationServiceInternal.asMap());
        }
        return slaveConfig;
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

    @javax.inject.Inject
    ConfigurationServiceInternal configurationServiceInternal;

}
