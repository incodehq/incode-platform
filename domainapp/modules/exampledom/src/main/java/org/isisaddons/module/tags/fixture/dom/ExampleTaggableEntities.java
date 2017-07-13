package org.isisaddons.module.tags.fixture.dom;

import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService
@DomainServiceLayout(
        menuOrder = "10"
)
public class ExampleTaggableEntities {

    //region > identification in the UI
    // //////////////////////////////////////

    public String getId() {
        return "simple";
    }

    public String iconName() {
        return "SimpleObject";
    }

    //endregion

    //region > listAll (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<ExampleTaggableEntity> listAll() {
        return container.allInstances(ExampleTaggableEntity.class);
    }

    //endregion

    //region > create (action)
    // //////////////////////////////////////
    
    @MemberOrder(sequence = "2")
    public ExampleTaggableEntity create(
            final @ParameterLayout(named="Name") String name,
            final @ParameterLayout(named="Brand") String brand,
            final @ParameterLayout(named="Sector") String sector) {
        final ExampleTaggableEntity obj = container.newTransientInstance(ExampleTaggableEntity.class);
        obj.setName(name);
        obj.setBrand(brand);
        obj.setSector(sector);
        container.persistIfNotAlready(obj);
        return obj;
    }

    //endregion

    //region > injected services
    // //////////////////////////////////////

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
