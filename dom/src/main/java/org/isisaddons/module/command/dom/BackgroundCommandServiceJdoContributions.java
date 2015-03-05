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
import org.isisaddons.module.command.CommandModule;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.command.Command;


/**
 * This service contributes a <tt>childCommands</tt> collection and a <tt>sublingCommands</tt> collection to
 * any {@link CommandJdo} entity.
 */
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class BackgroundCommandServiceJdoContributions extends AbstractFactoryAndRepository {

    public static abstract class ActionDomainEvent extends CommandModule.ActionDomainEvent<BackgroundCommandServiceJdoContributions> {
        public ActionDomainEvent(BackgroundCommandServiceJdoContributions source, Identifier identifier, Object... args) {
            super(source, identifier, args);
        }
    }

    // //////////////////////////////////////

    public static class ChildCommandsDomainEvent extends ActionDomainEvent {
        public ChildCommandsDomainEvent(BackgroundCommandServiceJdoContributions source, Identifier identifier, Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = ChildCommandsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "100.100")
    public List<CommandJdo> childCommands(final CommandJdo parent) {
        return backgroundCommandRepository.findByParent(parent);
    }

    // //////////////////////////////////////

    public static class SiblingCommandsDomainEvent extends ActionDomainEvent {
        public SiblingCommandsDomainEvent(final BackgroundCommandServiceJdoContributions source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = SiblingCommandsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    @MemberOrder(sequence = "100.110")
    public List<CommandJdo> siblingCommands(final CommandJdo siblingCommand) {
        final Command parent = siblingCommand.getParent();
        if(parent == null || !(parent instanceof CommandJdo)) {
            return Collections.emptyList();
        }
        final CommandJdo parentJdo = (CommandJdo) parent;
        final List<CommandJdo> siblingCommands = backgroundCommandRepository.findByParent(parentJdo);
        siblingCommands.remove(siblingCommand);
        return siblingCommands;
    }
    public boolean hideSiblingCommands(final CommandJdo siblingCommand) {
        final Command parent = siblingCommand.getParent();
        return parent == null || !(parent instanceof CommandJdo);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private BackgroundCommandServiceJdoRepository backgroundCommandRepository;
    
}
