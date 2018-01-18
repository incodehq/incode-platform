package org.isisaddons.module.command.dom;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.cmd.v1.CommandsDto;

import org.isisaddons.module.command.CommandModule;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isiscommand.CommandReplayMenu"
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "20.2"
)
public class CommandReplayMenu {

    public static abstract class PropertyDomainEvent<T>
            extends CommandModule.PropertyDomainEvent<CommandReplayMenu, T> { }

    public static abstract class CollectionDomainEvent<T>
            extends CommandModule.CollectionDomainEvent<CommandReplayMenu, T> { }

    public static abstract class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandReplayMenu> {
    }


    //region > findCommandsSince

    public static class FindCommandsSinceDomainEvent extends ActionDomainEvent { }

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
     * @param count - the maximum number of commands to return.  If not specified, all found will be returned.
     *
     * @return
     * @throws NotFoundException - if the command with specified transaction cannot be found.
     */
    @Action(
            domainEvent = FindCommandsSinceDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-files-o"
    )
    @MemberOrder(sequence="40")
    public List<CommandJdo> findCommandsSince(
            @Nullable
            @ParameterLayout(named="Transaction Id")
            final UUID transactionId,
            @Nullable
            @ParameterLayout(named="Count")
            final Integer count)
            throws NotFoundException {
        final List<CommandJdo> commands = commandServiceRepository.findSince(transactionId, count);
        if(commands == null) {
            throw new NotFoundException(transactionId);
        }
        return commands;
    }

    //endregion

    //region > downloadCommandsSince

    public static class DownloadCommandsSinceDomainEvent extends ActionDomainEvent { }

    /**
     * These actions should be called with HTTP Accept Header set to:
     * <code>application/xml;profile="urn:org.restfulobjects:repr-types/action-result";x-ro-domain-type="org.apache.isis.schema.cmd.v1.CommandsDto"</code>
     *
     * @param transactionId - to search from.  This transactionId will <i>not</i> be included in the response.
     * @param count - the maximum number of commands to return.  If not specified, all found will be returned.
     *
     * @return
     * @throws NotFoundException - if the command with specified transaction cannot be found.
     */
    @Action(
            domainEvent = DownloadCommandsSinceDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-download"
    )
    @MemberOrder(sequence="50")
    public Clob downloadCommandsSince(
            @Nullable
            @ParameterLayout(named="Transaction Id")
            final UUID transactionId,
            @Nullable
            @ParameterLayout(named="Count")
            final Integer count,
            @ParameterLayout(named="Filename prefix")
            final String fileNamePrefix) {
        final List<CommandJdo> commands = commandServiceRepository.findSince(transactionId, count);
        if(commands == null) {
            messageService.informUser("No commands found");
        }

        final CommandsDto commandsDto = new CommandsDto();
        for (final CommandJdo commandJdo : commands) {
            final CommandDto commandDto = commandJdo.asDto();
            commandsDto.getCommandDto().add(commandDto);
        }

        final String fileName = String.format(
                "%s_%s.xml", fileNamePrefix, elseDefault(transactionId));

        final String xml = jaxbService.toXml(commandsDto);
        return new Clob(fileName, "application/xml", xml);
    }

    private String elseDefault(final UUID transactionId) {
        return transactionId != null ? transactionId.toString() : "00000000-0000-0000-0000-000000000000";
    }

    public String default2DownloadCommandsSince() {
        return "commands";
    }

    //endregion

    //region > uploadCommands

    public static class UploadCommandsDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = UploadCommandsDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "fa-upload"
    )
    @MemberOrder(sequence="60")
    public void uploadCommands(final Clob commandsDtoAsXml) {
        final CharSequence chars = commandsDtoAsXml.getChars();
        final CommandsDto commandsDto = jaxbService.fromXml(CommandsDto.class, chars.toString());
        final List<CommandDto> commandDtoList = commandsDto.getCommandDto();
        for (final CommandDto commandDto : commandDtoList) {
            commandServiceRepository.save(commandDto);
        }
    }

    //endregion


    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceRepository;

    @javax.inject.Inject
    JaxbService jaxbService;

    @javax.inject.Inject
    MessageService messageService;

}

