package domainapp.modules.exampledom.wicket.fullcalendar2.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class FullCalendar2WicketTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public FullCalendar2WicketTearDownFixture(final String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"FullCalendar2WicketToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"Fullcalendar2WicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"FullCalendar2WicketToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"Fullcalendar2WicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"FullCalendar2WicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"FullCalendar2WicketToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"FullCalendar2WicketToDoItem\"");
        }
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
