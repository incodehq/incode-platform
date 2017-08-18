package org.incode.domainapp.example.dom.dom.tags.dom.demo;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDomTags.Taggable Entities"
)
@DomainServiceLayout(
        named = "Dom Modules",
        menuOrder = "30.2"
)
public class ExampleTaggableEntityMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<ExampleTaggableEntity> listAllTaggableEntities() {
        return container.allInstances(ExampleTaggableEntity.class);
    }


    @MemberOrder(sequence = "2")
    public ExampleTaggableEntity createTaggableEntity(
            final String name,
            final String brand,
            final String sector) {
        final ExampleTaggableEntity obj = container.newTransientInstance(ExampleTaggableEntity.class);
        obj.setName(name);
        obj.setBrand(brand);
        obj.setSector(sector);
        container.persistIfNotAlready(obj);
        return obj;
    }


    @javax.inject.Inject 
    DomainObjectContainer container;


}
