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

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.HEAD;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Command.ExecuteIn;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.background.ActionInvocationMemento;
import org.apache.isis.applib.services.background.BackgroundCommandService2;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.schema.cmd.v1.CommandMementoDto;
import org.apache.isis.schema.common.v1.OidDto;
import org.apache.isis.schema.utils.CommonDtoUtils;


/**
 * Persists a {@link ActionInvocationMemento memento-ized} action such that it can be executed asynchronously,
 * for example through a Quartz scheduler (using
 * {@link BackgroundCommandExecutionFromBackgroundCommandServiceJdo}).
 *
 * <p>
 * This implementation has no UI and there are no other implementations of the service API, and so it annotated
 * with {@link org.apache.isis.applib.annotation.DomainService}.  This class is implemented in the
 * <tt>o.a.i.module:isis-module-command-jdo</tt> module.  If that module is included in the classpath, it this means
 * that this service is automatically registered; no further configuration is required.
 *
 * <p>
 * (That said, do note that other services in the <tt>o.a.i.module:isis-module-command-jdo</tt> do require explicit
 * registration as services, eg in <tt>isis.properties</tt>).
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class BackgroundCommandServiceJdo extends AbstractService implements BackgroundCommandService2 {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(BackgroundCommandServiceJdo.class);


    //region > schedule (API - deprecated)


    @Deprecated
    @Programmatic
    @Override
    public void schedule(
            final ActionInvocationMemento aim, 
            final Command parentCommand, 
            final String targetClassName, 
            final String targetActionName, 
            final String targetArgs) {
        
        final String commandMemento = aim.asMementoString();
        final String targetStr = aim.getTarget().toString();
        final String actionId = aim.getActionId();

        persist(parentCommand, targetClassName, targetActionName, targetArgs, commandMemento, targetStr, actionId);
    }

    //endregion

    //region > schedule (API - deprecated)

    @Programmatic
    @Override
    public void schedule(
            final CommandMementoDto dto,
            final Command parentCommand,
            final String targetClassName,
            final String targetActionName,
            final String targetArgs) {

        final String commandMemento = jaxbService.toXml(dto);

        final List<OidDto> targetOidDtos = dto.getTargets();
        final String targetStr = Joiner.on("; ").join(Iterables.transform(targetOidDtos, CommonDtoUtils.OID_DTO_2_STR));

        final String actionId = dto.getAction().getActionIdentifier();
        persist(parentCommand, targetClassName, targetActionName, targetArgs, commandMemento, targetStr, actionId);
    }

    //endregion

    //region > helpers

    private void persist(
            final Command parentCommand,
            final String targetClassName,
            final String targetActionName,
            final String targetArgs,
            final String commandMemento,
            final String targetStr,
            final String actionId) {

        final UUID transactionId = UUID.randomUUID();
        final String user = parentCommand.getUser();

        final CommandJdo backgroundCommand = repositoryService.instantiate(CommandJdo.class);

        if(repositoryService.isPersistent(parentCommand)) {
            backgroundCommand.setParent(parentCommand);
        }

        backgroundCommand.setTransactionId(transactionId);

        backgroundCommand.setUser(user);
        backgroundCommand.setTimestamp(clockService.nowAsJavaSqlTimestamp());

        backgroundCommand.setExecuteIn(ExecuteIn.BACKGROUND);

        backgroundCommand.setTargetClass(targetClassName);
        backgroundCommand.setTargetAction(targetActionName);
        backgroundCommand.setTargetStr(targetStr);
        backgroundCommand.setMemberIdentifier(actionId);

        backgroundCommand.setArguments(targetArgs);

        backgroundCommand.setMemento(commandMemento);

        backgroundCommand.setPersistHint(true);

        repositoryService.persist(backgroundCommand);
    }

    @Inject
    RepositoryService repositoryService;

    //endregion


    @Inject
    JaxbService jaxbService;
    @Inject
    ClockService clockService;

}

