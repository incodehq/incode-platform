package org.incode.domainapp.example.dom.lib.stringinterpolator.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.demo.dom.todo2.DemoToDoItem2;
import org.incode.domainapp.example.dom.demo.dom.todo2.DemoToDoItem2Menu;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoToDoItem2TearDown;

public class StringInterpolatorDemoToDoItemsFixture extends DiscoverableFixtureScript {

    private final String user;

    public StringInterpolatorDemoToDoItemsFixture() {
        this(null);
    }

    public StringInterpolatorDemoToDoItemsFixture(final String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoToDoItem2TearDown());

        installFor(executionContext);

        getContainer().flush();
    }

    private void installFor(final ExecutionContext executionContext) {

        executionContext.add(this, createToDoItem("Documentation page - Review main Isis doc page", "documentation.html"));
        executionContext.add(this, createToDoItem("Screenshots - Review Isis screenshots", "intro/elevator-pitch/isis-in-pictures.html"));
        executionContext.add(this, createToDoItem("Lookup some Isis articles", "intro/learning-more/articles-and-presentations.html"));
        executionContext.add(this, createToDoItem("Learn about profiling in Isis", "reference/services/command-context.html"));

        transactionService.flushTransaction();
    }


    // //////////////////////////////////////

    private DemoToDoItem2 createToDoItem(final String description, final String documentationPage) {
        return toDoItem2Menu.newToDo(description, documentationPage);
    }


    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    DemoToDoItem2Menu toDoItem2Menu;


}
