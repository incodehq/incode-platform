package org.isisaddons.module.publishmq.dom.jdo.mixins;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;

@Mixin
public class PublishedEvent_siblingEvents {


    public static class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<PublishedEvent_siblingEvents> { }


    private final PublishedEvent publishedEvent;

    public PublishedEvent_siblingEvents(final PublishedEvent publishedEvent) {
        this.publishedEvent = publishedEvent;
    }

    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(sequence = "100.110")
    public List<PublishedEvent> $$() {
        final List<PublishedEvent> eventList = publishedEventRepository
                .findByTransactionId(publishedEvent.getTransactionId());
        eventList.remove(publishedEvent);
        return eventList;
    }

    @javax.inject.Inject
    private PublishedEventRepository publishedEventRepository;
    
}
