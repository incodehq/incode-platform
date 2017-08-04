package org.incode.domainapp.example.dom.spi.audit.dom.demo.audited;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.xactn.TransactionService;

@DomainService (
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleSpiAudit.SomeAuditedObjects",
        repositoryFor = SomeAuditedObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class SomeAuditedObjects {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SomeAuditedObject> listAll() {
        return repositoryService.allInstances(SomeAuditedObject.class);
    }



    @MemberOrder(sequence = "2")
    public SomeAuditedObject create(final String name) {
        final SomeAuditedObject obj = new SomeAuditedObject(name, null);
        repositoryService.persist(obj);
        return obj;
    }


    @Programmatic
    public List<SomeAuditedObject> delete(final SomeAuditedObject object) {
        repositoryService.remove(object);
        transactionService.flushTransaction();
        return listAll();
    }



    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TransactionService transactionService;


}
