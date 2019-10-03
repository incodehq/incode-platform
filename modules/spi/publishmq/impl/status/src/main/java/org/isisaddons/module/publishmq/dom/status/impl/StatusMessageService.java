package org.isisaddons.module.publishmq.dom.status.impl;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandPersistence;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_REST_ONLY,
        objectType = "isispublishmq.StatusMessageService"
)
public class StatusMessageService {

    /**
     * Used within the URL (rather than the fully-qualified class name).
     */
    public String id() {
        return "StatusMessageService";
    }

    /**
     * @deprecated - use {@link #logMessage(String, int, String, String, String, Integer, String)} (supplying sequence number also) instead.
     */
    @Deprecated
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            commandPersistence = CommandPersistence.NOT_PERSISTED,
            publishing = Publishing.DISABLED
    )
    public void log(
            @ParameterLayout(named = "transactionId")
            final String transactionId,
            @ParameterLayout(named = "message")
            final String message,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "oid")
            final String oid,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "uri")
            final String uri,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "status")
            final Integer status,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "detail")
            final String detail) {

        logMessage(transactionId, 0, message, oid, uri, status, detail);
    }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            commandPersistence = CommandPersistence.NOT_PERSISTED,
            publishing = Publishing.DISABLED
    )
    public void logMessage(
            @ParameterLayout(named = "transactionId")
            final String transactionId,
            @ParameterLayout(named = "sequence")
            final int sequence,
            @ParameterLayout(named = "message")
            final String message,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "oid")
            final String oid,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "uri")
            final String uri,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "status")
            final Integer status,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "detail")
            final String detail) {

        final StatusMessage statusMessage = repositoryService.instantiate(StatusMessage.class);

        statusMessage.setTimestamp(clockService.nowAsJavaSqlTimestamp());
        statusMessage.setTransactionId(UUID.fromString(transactionId));
        statusMessage.setSequence(sequence);
        statusMessage.setMessage(message);
        statusMessage.setOid(oid);
        statusMessage.setUri(uri);
        statusMessage.setStatus(status);
        statusMessage.setDetail(detail);

        repositoryService.persist(statusMessage);
    }

    //region > helpers

    @Inject
    RepositoryService repositoryService;

    @Inject
    ClockService clockService;

}
