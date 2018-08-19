package org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class WickedChartsDemoToDoItem_tearDown2 extends FixtureScript {

    private final String user;

    public WickedChartsDemoToDoItem_tearDown2() {
        this(null);
    }

    public WickedChartsDemoToDoItem_tearDown2(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : userService.getUser().getName();

        isisJdoSupport.executeUpdate(String.format(
                "delete "
                        + "from \"wickedChartsFixture\".\"WickedChartsDemoToDoItemDependencies\" "
                        + "where \"dependingId\" IN "
                        + "(select \"id\" from \"wickedChartsFixture\".\"WickedChartsDemoToDoItem\" where \"ownedBy\" = '%s') ",
                ownedBy));

        isisJdoSupport.executeUpdate(String.format(
                "delete from \"wickedChartsFixture\".\"WickedChartsDemoToDoItem\" "
                        + "where \"ownedBy\" = '%s'", ownedBy));
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
