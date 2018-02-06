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
package org.isisaddons.module.command.replay.impl;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.CommandExecutorService;
import org.apache.isis.applib.services.command.CommandWithDto;

/**
 * Override of {@link CommandExecutorService} that also sets the time (using the {@link TickingClockService}) to that
 * of the {@link Command}'s {@link Command#getTimestamp() timestamp} before executing the command.
 *
 * <p>
 *     It then delegates down to the default implementation.
 * </p>
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        menuOrder = "1000" // comes before the default implementation
)
public class CommandExecutorServiceWithTime implements CommandExecutorService {


    @Override
    public void executeCommand(final SudoPolicy sudoPolicy, final CommandWithDto commandWithDto) {
        tickingClockService.at(commandWithDto.getTimestamp(),
                () -> getDelegate().executeCommand(sudoPolicy, commandWithDto));
    }


    // //////////////////////////////////////

    /**
     * lazily populated
     */
    private CommandExecutorService delegate;

    private CommandExecutorService getDelegate() {
        if(delegate == null) {
            // find the first implementation that isn't this one
            for (final CommandExecutorService commandExecutorService : commandExecutorServices) {
                if(commandExecutorService != this) {
                    delegate = commandExecutorService;
                    break;
                }
            }
        }
        return delegate;
    }

    @javax.inject.Inject
    List<CommandExecutorService> commandExecutorServices;

    @javax.inject.Inject
    TickingClockService tickingClockService;


}
