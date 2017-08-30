#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.demo.dom.reminder;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDemo.DemoReminderMenu"
)
@DomainServiceLayout(
        named = "More Demo",
        menuOrder = "20.6"
)
public class DemoReminderMenu {



    @MemberOrder(sequence = "40")
    public DemoReminder newReminder(
            @Parameter(regexPattern = "${symbol_escape}${symbol_escape}w[@&:${symbol_escape}${symbol_escape}-${symbol_escape}${symbol_escape},${symbol_escape}${symbol_escape}.${symbol_escape}${symbol_escape}+ ${symbol_escape}${symbol_escape}w]*")
            final String description,
            final String documentationPage) {

        final DemoReminder reminder = new DemoReminder(description, documentationPage);

        container.persist(reminder);
        container.flush();

        return reminder;
    }


    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "50")
    public List<DemoReminder> listAllReminders() {
        return container.allInstances(DemoReminder.class);
    }


    @javax.inject.Inject
    DomainObjectContainer container;

}
