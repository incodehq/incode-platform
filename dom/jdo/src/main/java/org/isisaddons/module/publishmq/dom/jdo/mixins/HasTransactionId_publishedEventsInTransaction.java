package org.isisaddons.module.publishmq.dom.jdo.mixins;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEvent;
import org.isisaddons.module.publishmq.dom.jdo.events.PublishedEventRepository;

/**
 * For other implementations of {@link HasTransactionId} (<tt>AuditEntry</tt> from isis-module-audit, and
 * <tt>CommandJdo</tt> from isis-module-command), contribute a collection of {@link PublishedEvent}s that were also
 * emitted within the same transaction.
 */
@Mixin
public class HasTransactionId_publishedEventsInTransaction {

    private final HasTransactionId hasTransactionId;

    public HasTransactionId_publishedEventsInTransaction(HasTransactionId hasTransactionId) {
        this.hasTransactionId = hasTransactionId;
    }



    public static class ActionDomainEvent
            extends PublishMqModule.ActionDomainEvent<HasTransactionId> {
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
    @MemberOrder(sequence = "80.100")
    public List<PublishedEvent> $$() {
        return publishedEventRepository.findByTransactionId(hasTransactionId.getTransactionId());
    }
    /**
     * Hide if the contributee is itself a {@link PublishedEvent}.
     */
    public boolean hide$$() {
        return (hasTransactionId instanceof PublishedEvent);
    }



    @javax.inject.Inject
    private PublishedEventRepository publishedEventRepository;

}
