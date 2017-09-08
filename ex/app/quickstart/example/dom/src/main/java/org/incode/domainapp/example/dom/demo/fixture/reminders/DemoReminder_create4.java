package org.incode.domainapp.example.dom.demo.fixture.reminders;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminder;
import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminderMenu;

public class DemoReminder_create4 extends FixtureScript {


    @Override
    public void execute(final ExecutionContext ec) {

        createToDoItem("Documentation page - Review main Isis doc page", "documentation.html", ec);
        createToDoItem("Screenshots - Review Isis screenshots", "pages/isis-in-pictures/isis-in-pictures.html", ec);
        createToDoItem("Lookup some Isis articles", "pages/articles-and-presentations//articles-and-presentations.html", ec);
        createToDoItem("Learn about profiling in Isis", "guides/rgsvc/rgsvc.html#_rgsvc_application-layer-api_CommandContext", ec);

        transactionService.flushTransaction();
    }

    private DemoReminder createToDoItem(
            final String description,
            final String documentationPage,
            final ExecutionContext ec) {
        final DemoReminder reminder = reminderMenu.newReminder(description, documentationPage);
        ec.addResult(this,reminder);
        return reminder;
    }


    @javax.inject.Inject
    DemoReminderMenu reminderMenu;


}
