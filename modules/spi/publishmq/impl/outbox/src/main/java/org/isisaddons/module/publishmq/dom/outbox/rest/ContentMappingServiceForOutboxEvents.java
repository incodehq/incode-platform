package org.isisaddons.module.publishmq.dom.outbox.rest;

import java.io.StringReader;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.MediaType;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.conmap.ContentMappingService;
import org.apache.isis.applib.util.JaxbUtil;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEvent;
import org.isisaddons.module.publishmq.dom.outbox.events.PublishedEventType;
import org.incode.platform.publish.outbox.schema.InteractionsDtoUtil;
import org.incode.platform.publish.outbox.schema.ixl.v1.InteractionType;
import org.incode.platform.publish.outbox.schema.ixl.v1.InteractionsDto;

@DomainService(nature = NatureOfService.DOMAIN)
public class ContentMappingServiceForOutboxEvents implements ContentMappingService {

    @Override
    public Object map(final Object object, final List<MediaType> acceptableMediaTypes) {
        final boolean supported = Util.isSupported(InteractionsDto.class, acceptableMediaTypes);
        if(!supported) {
            return null;
        }

        return map(object);
    }

    private Object map(final Object object) {
        if (!(object instanceof OutboxEvents)) {
            return null;
        }

        final OutboxEvents outboxEvents = (OutboxEvents) object;

        final InteractionsDto dto = new InteractionsDto();

        final List<OutboxEvent> events = outboxEvents.getEvents();
        events.stream().map(this::toDto)
                .filter(Objects::nonNull)
                .forEach(dto.getInteraction()::add);

        return dto;
    }

    private InteractionType toDto(final OutboxEvent outboxEvent) {
        final String serializedForm = outboxEvent.getSerializedForm();

        final PublishedEventType eventType = outboxEvent.getEventType();
        switch (eventType) {
            case ACTION_INVOCATION:
            case PROPERTY_EDIT:
                final InteractionDto interactionDto = JaxbUtil
                        .fromXml(new StringReader(serializedForm), InteractionDto.class);
                return InteractionsDtoUtil.toType(interactionDto);
            default:
            case CHANGED_OBJECTS:
                return null;
        }
    }

}
