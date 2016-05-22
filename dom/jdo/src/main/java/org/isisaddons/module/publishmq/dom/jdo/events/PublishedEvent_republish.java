package org.isisaddons.module.publishmq.dom.jdo.events;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.schema.ixn.v1.InteractionDto;
import org.apache.isis.schema.utils.InteractionDtoUtils;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.servicespi.PublisherServiceUsingActiveMq;

@Mixin
public class PublishedEvent_republish {

    private final PublishedEvent publishedEvent;

    public PublishedEvent_republish(PublishedEvent publishedEvent) {
        this.publishedEvent = publishedEvent;
    }


    public static class ActionDomainEvent extends PublishMqModule.ActionDomainEvent<PublishedEvent_republish> { }

    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            cssClassFa = "share-alt",
            cssClass = "btn-warning"
    )
    @MemberOrder(name = "transactionId", sequence = "1")
    public PublishedEvent $$() {

        final String xml = publishedEvent.getSerializedForm();

        final PublishedEventType eventType = publishedEvent.getEventType();
        switch (eventType) {
        case ACTION_INVOCATION:
        case PROPERTY_EDIT:
            final InteractionDto interactionDto = InteractionDtoUtils.fromXml(xml);
            publisherService.republish(interactionDto);
            break;
        case CHANGED_OBJECTS:
            break;
        }
        return publishedEvent;
    }

    @Inject
    PublisherServiceUsingActiveMq publisherService;

}
