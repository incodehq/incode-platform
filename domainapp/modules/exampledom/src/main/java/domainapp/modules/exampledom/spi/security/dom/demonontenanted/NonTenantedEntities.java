package domainapp.modules.exampledom.spi.security.dom.demonontenanted;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isissecurityDemo.NonTenantedEntities",
        repositoryFor = NonTenantedEntity.class
)
@DomainServiceLayout(menuOrder = "10")
public class NonTenantedEntities {

    //region > identification in the UI
    // //////////////////////////////////////

    @Programmatic
    public String getId() {
        return "nonTenantedEntities";
    }

    public String iconName() {
        return "NonTenantedEntity";
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
    public List<NonTenantedEntity> listAll() {
        return container.allInstances(NonTenantedEntity.class);
    }

    //endregion

    //region > create (action)
    // //////////////////////////////////////
    
    @MemberOrder(sequence = "2")
    public NonTenantedEntity create(
            @Parameter(maxLength = NonTenantedEntity.MAX_LENGTH_NAME)
            @ParameterLayout(named="Name")
            final String name) {
        final NonTenantedEntity obj = container.newTransientInstance(NonTenantedEntity.class);
        obj.setName(name);
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
