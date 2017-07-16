package domainapp.modules.exampledom.wicket.excel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ExcelWicketTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public ExcelWicketTearDownFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"ExcelWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"ExcelWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"ExcelWicketToDoItem\"");
        }
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
