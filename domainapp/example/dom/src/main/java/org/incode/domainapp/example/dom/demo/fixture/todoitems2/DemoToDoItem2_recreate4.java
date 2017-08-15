package org.incode.domainapp.example.dom.demo.fixture.todoitems2;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.demo.dom.todo2.DemoToDoItem2;
import org.incode.domainapp.example.dom.demo.dom.todo2.DemoToDoItem2Menu;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoToDoItem2_tearDown;

public class DemoToDoItem2_recreate4 extends DiscoverableFixtureScript {


    @Override
    public void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoToDoItem2_tearDown());

        createToDoItem("Documentation page - Review main Isis doc page", "documentation.html", ec);
        createToDoItem("Screenshots - Review Isis screenshots", "intro/elevator-pitch/isis-in-pictures.html", ec);
        createToDoItem("Lookup some Isis articles", "intro/learning-more/articles-and-presentations.html", ec);
        createToDoItem("Learn about profiling in Isis", "reference/services/command-context.html", ec);

        transactionService.flushTransaction();
    }

    private DemoToDoItem2 createToDoItem(
            final String description,
            final String documentationPage,
            final ExecutionContext ec) {
        final DemoToDoItem2 item2 = toDoItem2Menu.newToDo(description, documentationPage);
        ec.addResult(this,item2);
        return item2;
    }


    @javax.inject.Inject
    DemoToDoItem2Menu toDoItem2Menu;


}
