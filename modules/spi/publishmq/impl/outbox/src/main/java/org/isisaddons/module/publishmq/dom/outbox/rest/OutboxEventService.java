package org.isisaddons.module.publishmq.dom.outbox.rest;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEvent;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEventRepository;

@DomainService(
        nature = NatureOfService.VIEW_REST_ONLY,
        objectType = "isispublishmq.OutboxEventService"
)
public class OutboxEventService {

    /**
     * This action is intended to be invoked with <code>Accept</code> header set to
     * <code>application/xml;profile=urn:org.restfulobjects:repr-types/action-result;x-ro-domain-type=org.incode.platform.publish.outbox.schema.ixl.v1.InteractionsDto</code>
     *
     * <p>
     *     The {@link ContentMappingServiceForOutboxEvents} will then serialize the resultant {@link OutboxEvents} view model into XML.
     * </p>
     *
     * @return
     */
    @Action(semantics = SemanticsOf.SAFE)
    public OutboxEvents pending() {
        final OutboxEvents outboxEvents = serviceRegistry.injectServicesInto(new OutboxEvents());
        final List<OutboxEvent> oldest = outboxEventRepository.findOldest();
        outboxEvents.getEvents().addAll(oldest);
        return outboxEvents;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public void delete(final String transactionId, final int sequence) {
        outboxEventRepository.deleteByTransactionIdAndSequence(UUID.fromString(transactionId), sequence);
    }

    @Inject
    OutboxEventRepository outboxEventRepository;

    @Inject
    ServiceRegistry serviceRegistry;


}
