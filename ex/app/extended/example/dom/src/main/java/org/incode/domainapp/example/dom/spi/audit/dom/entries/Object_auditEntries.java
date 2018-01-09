package org.incode.domainapp.example.dom.spi.audit.dom.entries;

import java.util.List;

import org.datanucleus.enhancement.Persistable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.audit.AuditModule;
import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;

@Mixin
public class Object_auditEntries {

    public static class AuditEntriesDomainEvent extends AuditModule.ActionDomainEvent<Object_auditEntries> {
    }

    private final Object object;

    public Object_auditEntries(Object object) {
        this.object = object;
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
        final Bookmark bookmark = bookmarkService.bookmarkFor(object);
        return auditingServiceRepository.findByTargetAndFromAndTo(bookmark, null, null);
    }
    public boolean hideAuditEntries() {
        // because HasTransaction_auditEntries already contributes this same object
        return !(object instanceof Persistable) || object instanceof HasTransactionId;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    //endregion

}
