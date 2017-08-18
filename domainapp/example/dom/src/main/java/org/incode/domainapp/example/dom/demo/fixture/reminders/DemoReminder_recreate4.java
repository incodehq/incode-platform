package org.incode.domainapp.example.dom.demo.fixture.reminders;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminder;
import org.incode.domainapp.example.dom.demo.dom.reminder.DemoReminderMenu;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoReminder_tearDown;

public class DemoReminder_recreate4 extends DiscoverableFixtureScript {


    @Override
    public void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoReminder_tearDown());

        createToDoItem("Documentation page - Review main Isis doc page", "documentation.html", ec);
        createToDoItem("Screenshots - Review Isis screenshots", "intro/elevator-pitch/isis-in-pictures.html", ec);
        createToDoItem("Lookup some Isis articles", "intro/learning-more/articles-and-presentations.html", ec);
        createToDoItem("Learn about profiling in Isis", "reference/services/command-context.html", ec);

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
