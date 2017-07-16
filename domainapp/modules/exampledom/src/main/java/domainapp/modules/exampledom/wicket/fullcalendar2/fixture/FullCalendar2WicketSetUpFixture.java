package domainapp.modules.exampledom.wicket.fullcalendar2.fixture;

import domainapp.modules.exampledom.wicket.fullcalendar2.fixture.data.ToDoItemsFixture;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class FullCalendar2WicketSetUpFixture extends DiscoverableFixtureScript {

    private final String ownedBy;

    public FullCalendar2WicketSetUpFixture() {
        this(null);
    }

    public FullCalendar2WicketSetUpFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        execute(new FullCalendar2WicketTearDownFixture(ownedBy), executionContext);
        execute(new ToDoItemsFixture(), executionContext);
    }

}
