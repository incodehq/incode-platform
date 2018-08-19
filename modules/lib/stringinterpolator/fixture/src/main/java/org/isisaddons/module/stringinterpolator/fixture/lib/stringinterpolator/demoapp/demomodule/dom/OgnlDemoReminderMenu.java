package org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom;

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
        objectType = "libStringInterpolatorFixture.OgnlDemoReminderMenu"
)
@DomainServiceLayout(
        named = "Dummy",
        menuOrder = "20.6"
)
public class OgnlDemoReminderMenu {



    @MemberOrder(sequence = "40")
    public OgnlDemoReminder newReminder(
            @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
            final String description,
            final String documentationPage) {

        final OgnlDemoReminder reminder = new OgnlDemoReminder(description, documentationPage);

        container.persist(reminder);
        container.flush();

        return reminder;
    }


    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "50")
    public List<OgnlDemoReminder> listAllReminders() {
        return container.allInstances(OgnlDemoReminder.class);
    }


    @javax.inject.Inject
    DomainObjectContainer container;

}
