package org.isisaddons.module.publishmq.dom.status.events;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.RepresentsInteractionMemberExecution;
import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.dom.status.impl.StatusMessage;
import org.isisaddons.module.publishmq.dom.status.impl.StatusMessageRepository;

@Mixin
public class HasTransactionId_statusMessagesInTransaction {

    private final HasTransactionId hasTransactionId;
    public HasTransactionId_statusMessagesInTransaction(final HasTransactionId hasTransactionId) {
        this.hasTransactionId = hasTransactionId;
    }


    public static class ActionDomainEvent extends PublishMqModule.ActionDomainEvent<HasTransactionId_statusMessagesInTransaction> { }


    @Action(
        domainEvent = ActionDomainEvent.class,
        semantics = SemanticsOf.SAFE
    )
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(defaultView = "table")
    public List<StatusMessage> $$() {
        if(hasTransactionId instanceof RepresentsInteractionMemberExecution) {
            final RepresentsInteractionMemberExecution rime = (RepresentsInteractionMemberExecution) hasTransactionId;
            return statusMessageRepository.findByTransactionIdAndSequence(rime.getTransactionId(), rime.getSequence());
        } else {
            return statusMessageRepository.findByTransactionId(hasTransactionId.getTransactionId());
        }
    }


    @Inject
    StatusMessageRepository statusMessageRepository;

}
