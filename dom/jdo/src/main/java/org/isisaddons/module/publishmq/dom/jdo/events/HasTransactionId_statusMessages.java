package org.isisaddons.module.publishmq.dom.jdo.events;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.jdo.status.StatusMessage;
import org.isisaddons.module.publishmq.dom.jdo.status.StatusMessageRepository;

@Mixin
public class HasTransactionId_statusMessages {

    private final HasTransactionId hasTransactionId;
    public HasTransactionId_statusMessages(final HasTransactionId hasTransactionId) {
        this.hasTransactionId = hasTransactionId;
    }


    public static class ActionDomainEvent extends PublishMqModule.ActionDomainEvent<HasTransactionId_statusMessages> { }


    @Action(
        domainEvent = ActionDomainEvent.class,
        semantics = SemanticsOf.SAFE
    )
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(defaultView = "table")
    public List<StatusMessage> $$() {
        return statusMessageRepository.findByTransactionId(hasTransactionId.getTransactionId());
    }


    @Inject
    private StatusMessageRepository statusMessageRepository;

}
