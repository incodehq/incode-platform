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

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.command.Command;

import org.isisaddons.module.command.CommandModule;

@Mixin
public class CommandJdo_siblingCommands {


    public static class SiblingCommandsDomainEvent
            extends CommandModule.ActionDomainEvent<CommandJdo_siblingCommands> { }


    private final CommandJdo commandJdo;
    public CommandJdo_siblingCommands(final CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }


    @Action(
            domainEvent = SiblingCommandsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(sequence = "100.110")
    public List<CommandJdo> $$() {
        final Command parent = commandJdo.getParent();
        if(parent == null || !(parent instanceof CommandJdo)) {
            return Collections.emptyList();
        }
        final CommandJdo parentJdo = (CommandJdo) parent;
        final List<CommandJdo> siblingCommands = backgroundCommandRepository.findByParent(parentJdo);
        siblingCommands.remove(commandJdo);
        return siblingCommands;
    }
    public boolean hide$$() {
        final Command parent = commandJdo.getParent();
        return false; // parent == null || !(parent instanceof CommandJdo);
    }


    @javax.inject.Inject
    private BackgroundCommandServiceJdoRepository backgroundCommandRepository;
    
}
