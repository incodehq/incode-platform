package org.isisaddons.module.audit.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;

import org.isisaddons.module.audit.AuditModule;

@Mixin
public class HasTransactionId_auditEntries {

    public static class AuditEntriesDomainEvent extends AuditModule.ActionDomainEvent<HasTransactionId_auditEntries> {
    }

    private final HasTransactionId hasTransactionId;

    public HasTransactionId_auditEntries(HasTransactionId hasTransactionId) {
        this.hasTransactionId = hasTransactionId;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = AuditEntriesDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(sequence = "50.100")
    public List<AuditEntry> auditEntries() {
        return auditEntryRepository.findByTransactionId(hasTransactionId.getTransactionId());
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private AuditingServiceRepository auditEntryRepository;
    //endregion

}
