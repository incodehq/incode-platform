package org.isisaddons.module.command.dom;

import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Command.ExecuteIn;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.background.ActionInvocationMemento;
import org.apache.isis.applib.services.background.BackgroundCommandService2;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.common.v1.OidDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

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

        final CommandJdo backgroundCommand =
                newBackgroundCommand(parentCommand, targetClassName, targetActionName, targetArgs);

        backgroundCommand.setTargetStr(aim.getTarget().toString());
        backgroundCommand.setMemento(aim.asMementoString());
        backgroundCommand.setMemberIdentifier(aim.getActionId());

        repositoryService.persist(backgroundCommand);
    }

    @Override
    public void schedule(
            final CommandDto dto,
            final Command parentCommand,
            final String targetClassName,
            final String targetActionName,
            final String targetArgs) {

        final CommandJdo backgroundCommand =
                newBackgroundCommand(parentCommand, targetClassName, targetActionName, targetArgs);

        final OidDto firstTarget = dto.getTargets().getOid().get(0);
        backgroundCommand.setTargetStr(Bookmark.from(firstTarget).toString());
        backgroundCommand.setMemento(CommandDtoUtils.toXml(dto));
        backgroundCommand.setMemberIdentifier(dto.getMember().getMemberIdentifier());

        repositoryService.persist(backgroundCommand);

    }

    private CommandJdo newBackgroundCommand(
            final Command parentCommand,
            final String targetClassName,
            final String targetActionName,
            final String targetArgs) {

        final CommandJdo backgroundCommand = repositoryService.instantiate(CommandJdo.class);

        backgroundCommand.setParent(parentCommand);

        // workaround for ISIS-1472; parentCommand not properly set up if invoked via RO viewer
        if(parentCommand.getMemberIdentifier() == null) {
            backgroundCommand.setParent(null);
        }

        final UUID transactionId = UUID.randomUUID();
        final String user = parentCommand.getUser();

        backgroundCommand.setTransactionId(transactionId);

        backgroundCommand.setUser(user);
        backgroundCommand.setTimestamp(clockService.nowAsJavaSqlTimestamp());

        backgroundCommand.setExecuteIn(ExecuteIn.BACKGROUND);

        backgroundCommand.setTargetClass(targetClassName);
        backgroundCommand.setTargetAction(targetActionName);

        backgroundCommand.setArguments(targetArgs);

        backgroundCommand.setPersistHint(true);

        return backgroundCommand;
    }


    @Inject
    RepositoryService repositoryService;

    //endregion


    @Inject
    ClockService clockService;

}

