package org.isisaddons.module.command.dom;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.conmap.ContentMappingServiceForCommandDto;
import org.apache.isis.applib.conmap.ContentMappingServiceForCommandsDto;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.cmd.v1.CommandsDto;

import org.isisaddons.module.command.CommandModule;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isiscommand.CommandReplayOnMasterService"
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "20.2"
)
public class CommandReplayOnMasterService {

    public static abstract class PropertyDomainEvent<T>
            extends CommandModule.PropertyDomainEvent<CommandReplayOnMasterService, T> { }

    public static abstract class CollectionDomainEvent<T>
            extends CommandModule.CollectionDomainEvent<CommandReplayOnMasterService, T> { }

    public static abstract class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandReplayOnMasterService> {
    }


    //region > findCommandsOnMasterSince

    public static class FindCommandsOnMasterSinceDomainEvent extends ActionDomainEvent { }

    public static class NotFoundException extends ApplicationException {
        private static final long serialVersionUID = 1L;
        private final UUID transactionId;
        public NotFoundException(final UUID transactionId) {
            super("Transaction not found");
            this.transactionId = transactionId;
        }
        public UUID getTransactionId() {
            return transactionId;
        }
    }

    /**
     * These actions should be called with HTTP Accept Header set to:
     * <code>application/xml;profile="urn:org.restfulobjects:repr-types/action-result";x-ro-domain-type="org.apache.isis.schema.cmd.v1.CommandsDto"</code>
     *
     * @param transactionId - to search from.  This transactionId will <i>not</i> be included in the response.
     * @param batchSize - the maximum number of commands to return.  If not specified, all found will be returned.
     *
     * @return
     * @throws NotFoundException - if the command with specified transaction cannot be found.
     */
    @Action(
            domainEvent = FindCommandsOnMasterSinceDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-files-o"
    )
    @MemberOrder(sequence="40")
    public List<CommandJdo> findCommandsOnMasterSince(
            @Nullable
            @ParameterLayout(named="Transaction Id")
            final UUID transactionId,
            @Nullable
            @ParameterLayout(named="Batch size")
            final Integer batchSize)
            throws NotFoundException {
        final List<CommandJdo> commands = commandServiceRepository.findForegroundSince(transactionId, batchSize);
        if(commands == null) {
            throw new NotFoundException(transactionId);
        }
        return commands;
    }
    public Integer default1FindCommandsOnMasterSince() {
        return 25;
    }

    //endregion

    //region > downloadCommandsOnMasterSince

    public static class DownloadCommandsOnMasterSinceDomainEvent extends ActionDomainEvent { }

    /**
     * These actions should be called with HTTP Accept Header set to:
     * <code>application/xml;profile="urn:org.restfulobjects:repr-types/action-result";x-ro-domain-type="org.apache.isis.schema.cmd.v1.CommandsDto"</code>
     *
     * @param transactionId - to search from.  This transactionId will <i>not</i> be included in the response.
     * @param batchSize - the maximum number of commands to return.  If not specified, all found will be returned.
     *
     * @return
     * @throws NotFoundException - if the command with specified transaction cannot be found.
     */
    @Action(
            domainEvent = DownloadCommandsOnMasterSinceDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-download"
    )
    @MemberOrder(sequence="50")
    public Clob downloadCommandsOnMasterSince(
            @Nullable
            @ParameterLayout(named="Transaction Id")
            final UUID transactionId,
            @Nullable
            @ParameterLayout(named="Batch size")
            final Integer batchSize,
            @ParameterLayout(named="Filename prefix")
            final String fileNamePrefix) {
        final List<CommandJdo> commands = commandServiceRepository.findForegroundSince(transactionId, batchSize);
        if(commands == null) {
            messageService.informUser("No commands found");
        }

        final CommandsDto commandsDto =
                contentMappingServiceForCommandsDto.map(commands);

        final String fileName = String.format(
                "%s_%s.xml", fileNamePrefix, elseDefault(transactionId));

        final String xml = jaxbService.toXml(commandsDto);
        return new Clob(fileName, "application/xml", xml);
    }

    private String elseDefault(final UUID transactionId) {
        return transactionId != null ? transactionId.toString() : "00000000-0000-0000-0000-000000000000";
    }

    public Integer default1DownloadCommandsOnMasterSince() {
        return 25;
    }
    public String default2DownloadCommandsOnMasterSince() {
        return "commands_since";
    }

    //endregion

    //region > downloadCommandById

    public static class DownloadCommandOnMasterDomainEvent extends ActionDomainEvent { }

    /**
     * This action should be called with HTTP Accept Header set to:
     * <code>application/xml;profile="urn:org.restfulobjects:repr-types/action-result";x-ro-domain-type="org.apache.isis.schema.cmd.v1.CommandDto"</code>
     *
     * @param transactionId - to download.
     *
     * @return
     * @throws NotFoundException - if the command with specified transaction cannot be found.
     */
    @Action(
            domainEvent = DownloadCommandOnMasterDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-download"
    )
    @MemberOrder(sequence="50")
    public Clob downloadCommandById(
            @ParameterLayout(named="Transaction Id")
            final UUID transactionId,
            @ParameterLayout(named="Filename prefix")
            final String fileNamePrefix) {
        final CommandJdo commandJdo = commandServiceRepository.findByTransactionId(transactionId);
        if(commandJdo == null) {
            messageService.informUser("No command found");
        }

        final CommandDto commandDto =
                contentMappingServiceForCommandDto.map(commandJdo);

        final String fileName = String.format(
                "%s_%s.xml", fileNamePrefix, elseDefault(transactionId));

        final String xml = jaxbService.toXml(commandDto);
        return new Clob(fileName, "application/xml", xml);
    }

    public String default1DownloadCommandById() {
        return "command";
    }

    //endregion

    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceRepository;

    @javax.inject.Inject
    JaxbService jaxbService;

    @javax.inject.Inject
    MessageService messageService;

    @Inject
    ContentMappingServiceForCommandsDto contentMappingServiceForCommandsDto;

    @Inject
    ContentMappingServiceForCommandDto contentMappingServiceForCommandDto;
}

