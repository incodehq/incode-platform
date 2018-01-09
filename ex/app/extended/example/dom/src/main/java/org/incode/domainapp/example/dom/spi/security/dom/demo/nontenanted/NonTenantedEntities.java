package org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted;

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
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleSpiSecurity.NonTenantedEntities",
        repositoryFor = NonTenantedEntity.class
)
@DomainServiceLayout(
        named = "SPI Modules",
        menuOrder = "50.4.2"
)
public class NonTenantedEntities {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<NonTenantedEntity> listAllNonTenantedEntities() {
        return container.allInstances(NonTenantedEntity.class);
    }


    @MemberOrder(sequence = "2")
    public NonTenantedEntity createNonTenantedEntity(
            @Parameter(maxLength = NonTenantedEntity.MAX_LENGTH_NAME)
            final String name) {
        final NonTenantedEntity obj = new NonTenantedEntity(name, null);
        container.persistIfNotAlready(obj);
        return obj;
    }


    @javax.inject.Inject 
    DomainObjectContainer container;


}
