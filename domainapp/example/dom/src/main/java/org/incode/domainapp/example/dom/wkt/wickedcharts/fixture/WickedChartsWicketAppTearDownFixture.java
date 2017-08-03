package org.incode.domainapp.example.dom.wkt.wickedcharts.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class WickedChartsWicketAppTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public WickedChartsWicketAppTearDownFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"exampleWktWickedCharts\".\"WickedChartsWicketToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"exampleWktWickedCharts\".\"WickedChartsWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"exampleWktWickedCharts\".\"WickedChartsWicketToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"exampleWktWickedCharts\".\"WickedChartsWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"exampleWktWickedCharts\".\"WickedChartsWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"exampleWktWickedCharts\".\"WickedChartsWicketToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"exampleWktWickedCharts\".\"WickedChartsWicketToDoItem\"");
        }
    }


    @javax.inject.Inject
    private org.apache.isis.applib.services.jdosupport.IsisJdoSupport isisJdoSupport;

}
