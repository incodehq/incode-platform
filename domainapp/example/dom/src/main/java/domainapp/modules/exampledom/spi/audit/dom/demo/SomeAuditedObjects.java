package domainapp.modules.exampledom.spi.audit.dom.demo;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.xactn.TransactionService;

@DomainService (
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "org.isisaddons.module.audit.fixture.dom.audited.SomeAuditedObjects",
        repositoryFor = SomeAuditedObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class SomeAuditedObjects {

    //region > listAll (action)

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SomeAuditedObject> listAll() {
        return repositoryService.allInstances(SomeAuditedObject.class);
    }

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public SomeAuditedObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final SomeAuditedObject obj = repositoryService.instantiate(SomeAuditedObject.class);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > delete (action)

    @Programmatic
    public List<SomeAuditedObject> delete(final SomeAuditedObject object) {
        repositoryService.remove(object);
        transactionService.flushTransaction();
        return listAll();
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TransactionService transactionService;

    //endregion

}
