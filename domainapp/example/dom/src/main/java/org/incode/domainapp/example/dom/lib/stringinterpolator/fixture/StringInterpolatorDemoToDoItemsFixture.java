package org.incode.domainapp.example.dom.lib.stringinterpolator.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.incode.domainapp.example.dom.lib.stringinterpolator.dom.demo.StringInterpolatorDemoToDoItem;
import org.incode.domainapp.example.dom.lib.stringinterpolator.dom.demo.StringInterpolatorDemoToDoItems;

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

        isisJdoSupport.executeUpdate("delete from \"exampleLibStringInterpolator\".\"StringInterpolatorDemoToDoItem\"");

        installFor(executionContext);

        getContainer().flush();
    }

    private void installFor(final ExecutionContext executionContext) {

        executionContext.add(this, createToDoItem("Documentation page - Review main Isis doc page", "documentation.html"));
        executionContext.add(this, createToDoItem("Screenshots - Review Isis screenshots", "intro/elevator-pitch/isis-in-pictures.html"));
        executionContext.add(this, createToDoItem("Lookup some Isis articles", "intro/learning-more/articles-and-presentations.html"));
        executionContext.add(this, createToDoItem("Learn about profiling in Isis", "reference/services/command-context.html"));

        getContainer().flush();
    }


    // //////////////////////////////////////

    private StringInterpolatorDemoToDoItem createToDoItem(final String description, final String documentationPage) {
        return toDoItems.newToDo(description, documentationPage);
    }


    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private StringInterpolatorDemoToDoItems toDoItems;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
