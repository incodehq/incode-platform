package org.isisaddons.module.command.dom;

import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.Command.ExecuteIn;
import org.apache.isis.applib.annotation.Command.Persistence;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.Command.Executor;
import org.apache.isis.applib.services.command.spi.CommandService;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;

/**
 *
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        menuOrder = "100" // as of 1.13.0, the framework provides a default implementation; we override using this attribute
)
public class CommandServiceJdo implements CommandService {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(CommandServiceJdo.class);

    //region > create (API)
    /**
     * Creates an {@link CommandJdo}, initializing its 
     * {@link Command#setExecuteIn(org.apache.isis.applib.annotation.Command.ExecuteIn)} nature} to be
     * {@link org.apache.isis.applib.services.command.Command.Executor#OTHER rendering}.
     */
    @Programmatic
    @Override
    public Command create() {
        CommandJdo command = factoryService.instantiate(CommandJdo.class);
        command.setExecutor(Executor.OTHER);
        command.setPersistence(Persistence.IF_HINTED);
        return command;
    }
    //endregion

    //region > startTransaction (API, deprecated)

    @Deprecated
    @Programmatic
    @Override
    public void startTransaction(final Command command, final UUID transactionId) {
        // nothing to do.
    }
    //endregion

    //region > complete (API)

    @Programmatic
    @Override
    public void complete(final Command command) {
        final CommandJdo commandJdo = asUserInitiatedCommandJdo(command);
        if(commandJdo == null) {
            return;
        }

        commandServiceJdoRepository.persistIfHinted(commandJdo);

    }

    //endregion

    //region > persistIfPossible (API)

    @Override
    public boolean persistIfPossible(Command command) {
        if(!(command instanceof CommandJdo)) {
            // ought not to be the case, since this service created the object in the #create() method
            return false;
        }
        final CommandJdo commandJdo = (CommandJdo)command;
        repositoryService.persist(commandJdo);
        return true;
    }
    //endregion

    //region > helpers


    /**
     * Not API, also used by {@link CommandServiceJdoRepository}.
     */
    CommandJdo asUserInitiatedCommandJdo(final Command command) {
        if(!(command instanceof CommandJdo)) {
            // ought not to be the case, since this service created the object in the #create() method
            return null;
        }
        if(command.getExecuteIn() != ExecuteIn.FOREGROUND) {
            return null;
        } 
        final CommandJdo commandJdo = (CommandJdo) command;
        return commandJdo.shouldPersist()? commandJdo: null;
    }

    //endregion


    @Inject
    RepositoryService repositoryService;

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

    @Inject
    FactoryService factoryService;

}
