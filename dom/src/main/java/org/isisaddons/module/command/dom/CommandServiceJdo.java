/*
 *  Copyright 2013~2014 Dan Haywood
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
<<<<<<< HEAD
import org.apache.isis.applib.services.clock.ClockService;
=======
>>>>>>> changes for ISIS-1291
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.Command.Executor;
import org.apache.isis.applib.services.command.spi.CommandService;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

/**
 *
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
<<<<<<< HEAD
        menuOrder = "100" // take precedence over the default implementation
=======
        menuOrder = "100" // as of 1.13.0, the framework provides a default implementation; we override using this attribute
>>>>>>> changes for ISIS-1291
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

<<<<<<< HEAD
=======
        //
        // prior to 1.13.0 there was a guard here to not persist the command if the completedAt had been set.
        // as of 1.13.0, this field is populated by the framework, so the guard no longer makes sense.
        //

>>>>>>> changes for ISIS-1291
        // can't store target if too long (eg view models)
        if (commandJdo.getTargetStr() != null && commandJdo.getTargetStr().length() > JdoColumnLength.BOOKMARK) {
            commandJdo.setTargetStr(null);
        }

        if(commandJdo.shouldPersist()) {
            repositoryService.persist(commandJdo);
        }

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
    FactoryService factoryService;
    @Inject
    ClockService clockService;

}
