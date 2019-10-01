package org.isisaddons.module.publishmq.dom.jdo.events;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
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

        throw new RuntimeException("Not yet implemented.");
    }

}
