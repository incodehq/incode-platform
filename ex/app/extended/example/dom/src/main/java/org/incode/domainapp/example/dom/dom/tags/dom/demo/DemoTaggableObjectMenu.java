package org.incode.domainapp.example.dom.dom.tags.dom.demo;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.xactn.TransactionService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDomTags.DemoTaggableEntityMenu"
)
@DomainServiceLayout(
        named = "Subdomains",
        menuOrder = "30.2"
)
public class DemoTaggableObjectMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<DemoTaggableObject> listAllTaggableObjects() {
        return repositoryService.allInstances(DemoTaggableObject.class);
    }


    @MemberOrder(sequence = "2")
    public DemoTaggableObject createTaggableEntity(
            final String name,
            final String brand,
            final String sector) {
        final DemoTaggableObject obj = repositoryService.instantiate(DemoTaggableObject.class);
        obj.setName(name);
        repositoryService.persistAndFlush(obj);
        obj.setBrand(brand);
        obj.setSector(sector);
        transactionService.flushTransaction();
        return obj;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject TransactionService transactionService;


}
