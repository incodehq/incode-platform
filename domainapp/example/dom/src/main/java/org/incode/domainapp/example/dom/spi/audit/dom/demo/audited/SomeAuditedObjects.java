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

@DomainService (
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleSpiAudit.SomeAuditedObjects",
        repositoryFor = SomeAuditedObject.class
)
@DomainServiceLayout(
        named = "SPI Modules",
        menuOrder = "50.1.1"
)
public class SomeAuditedObjects {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SomeAuditedObject> listAllSomeAuditedObjects() {
        return repositoryService.allInstances(SomeAuditedObject.class);
    }



    @MemberOrder(sequence = "2")
    public SomeAuditedObject createSomeAuditedObject(final String name) {
        final SomeAuditedObject obj = new SomeAuditedObject(name, null);
        repositoryService.persist(obj);
        return obj;
    }


    @Programmatic
    public List<SomeAuditedObject> deleteSomeAuditedObject(final SomeAuditedObject object) {
        repositoryService.removeAndFlush(object);
        return listAllSomeAuditedObjects();
    }



    @javax.inject.Inject
    RepositoryService repositoryService;



}
