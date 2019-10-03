package org.isisaddons.module.publishmq.dom.jdo.events;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.outbox.events.OutboxEventRepository;
import org.isisaddons.module.publishmq.dom.outbox.events.PublishedEventType;

@Mixin(method = "act")
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
    public PublishedEvent act() {

        outboxEventRepository.upsert(
                publishedEvent.getTransactionId(),
                publishedEvent.getSequence(),
                publishedEvent.getEventType(),
                publishedEvent.getTimestamp(),
                publishedEvent.getUser(),
                publishedEvent.getTarget(), publishedEvent.getTargetClass(),
                publishedEvent.getMemberIdentifier(), publishedEvent.getTargetAction(),
                publishedEvent.getSerializedForm()
        );

        return publishedEvent;
    }

    public String disableAct() {
        return publishedEvent.getEventType() == PublishedEventType.CHANGED_OBJECTS ?
                "Cannot republish events of type 'CHANGED_OBJECTS'"
                : null;
    }

    @Inject
    OutboxEventRepository outboxEventRepository;
}
