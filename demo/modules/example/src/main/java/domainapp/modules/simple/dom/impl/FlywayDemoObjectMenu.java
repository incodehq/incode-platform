package domainapp.modules.simple.dom.impl;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = FlywayDemoObject.class
)
@DomainServiceLayout(
        named = "Simple Objects",
        menuOrder = "10"
)
public class FlywayDemoObjectMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<FlywayDemoObject> listAll() {
        return flywayDemoObjectRepository.listAll();
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<FlywayDemoObject> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return flywayDemoObjectRepository.findByName(name);
    }


    public static class CreateDomainEvent extends ActionDomainEvent<FlywayDemoObjectMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public FlywayDemoObject create(
            @ParameterLayout(named="Name")
            final String name) {
        return flywayDemoObjectRepository.create(name);
    }


    @javax.inject.Inject
    FlywayDemoObjectRepository flywayDemoObjectRepository;

}
