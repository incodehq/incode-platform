package domainapp.modules.simple.dom;

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

import domainapp.modules.base.togglz.TogglzFeature;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.SimpleObjectMenu",
        repositoryFor = SimpleObject.class
)
@DomainServiceLayout(
        named = "Simple Objects",
        menuOrder = "10"
)
public class SimpleObjectMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<SimpleObject> listAll() {
        return simpleObjectRepository.listAll();
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<SimpleObject> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return simpleObjectRepository.findByName(name);
    }

    public boolean hideFindByName() {
        return ! TogglzFeature.SimpleObject_findByName.isActive();
    }


    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjectMenu> {}
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT, domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public SimpleObject create(
            @ParameterLayout(named="Name")
            final String name) {
        return simpleObjectRepository.create(name);
    }
    public boolean hideCreate() {
        return ! TogglzFeature.SimpleObject_create.isActive();
    }


    @javax.inject.Inject
    SimpleObjectRepository simpleObjectRepository;

}
