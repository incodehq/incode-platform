package domainapp.modules.exampledom.lib.docx.dom.demo;

import java.util.List;
import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Order.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class Orders {

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
    public List<Order> listAll() {
        return container.allInstances(Order.class);
    }

    //endregion

    //region > create (action)
    // //////////////////////////////////////
    
    @MemberOrder(sequence = "2")
    public Order create(
            @ParameterLayout(named="Order Number")
            final String number,
            @ParameterLayout(named="Customer Name")
            final String customerName,
            @ParameterLayout(named="Order Date")
            final LocalDate date,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named="Preferences")
            final String preferences) {
        final Order obj = container.newTransientInstance(Order.class);
        obj.setNumber(number);
        obj.setDate(date);
        obj.setCustomerName(customerName);
        obj.setPreferences(preferences);
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
