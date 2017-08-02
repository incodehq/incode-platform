package domainapp.modules.exampledom.wicket.gmap3.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class Gmap3DemoTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public Gmap3DemoTearDownFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }


    @Override
    protected void execute(ExecutionContext executionContext) {

        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"Gmap3ToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"Gmap3ToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"Gmap3ToDoItem\"");
        }

    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
