package org.incode.domainapp.example.dom.spi.audit.dom.demo.notaudited;

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

import org.incode.domainapp.example.dom.spi.audit.dom.demo.audited.SomeAuditedObject;

@DomainService (
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleSpiAudit.SomeNotAuditedObjects",
        repositoryFor = SomeAuditedObject.class
)
@DomainServiceLayout(
        named = "SPI Modules",
        menuOrder = "50.1.2"
)
public class SomeNotAuditedObjects {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SomeNotAuditedObject> listAllSomeNotAuditedObjects() {
        return repositoryService.allInstances(SomeNotAuditedObject.class);
    }



    @MemberOrder(sequence = "2")
    public SomeNotAuditedObject createSomeNotAuditedObject(final String name) {
        final SomeNotAuditedObject obj = new SomeNotAuditedObject(name, null);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }

    @Programmatic
    public List<SomeNotAuditedObject> deleteSomeNotAuditedObject(final SomeNotAuditedObject object) {
        repositoryService.removeAndFlush(object);
        return listAllSomeNotAuditedObjects();
    }


    @javax.inject.Inject
    RepositoryService repositoryService;


}
