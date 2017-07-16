package domainapp.modules.exampledom.spi.audit.dom.nonaudited;

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

import domainapp.modules.exampledom.spi.audit.dom.audited.SomeAuditedObject;

@DomainService (
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "org.isisaddons.module.audit.fixture.dom.notaudited.SomeNotAuditedObjects",
        repositoryFor = SomeAuditedObject.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class SomeNotAuditedObjects {

    //region > listAll (action)

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SomeNotAuditedObject> listAll() {
        return repositoryService.allInstances(SomeNotAuditedObject.class);
    }

    //endregion

    //region > create (action)

    @MemberOrder(sequence = "2")
    public SomeNotAuditedObject create(
            @ParameterLayout(named = "Name")
            final String name) {
        final SomeNotAuditedObject obj = repositoryService.instantiate(SomeNotAuditedObject.class);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > delete (action)

    @Programmatic
    public List<SomeNotAuditedObject> delete(final SomeNotAuditedObject object) {
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
