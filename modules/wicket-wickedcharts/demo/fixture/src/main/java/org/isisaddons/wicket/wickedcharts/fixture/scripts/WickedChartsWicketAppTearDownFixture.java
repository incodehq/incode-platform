package org.isisaddons.wicket.wickedcharts.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class WickedChartsWicketAppTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public WickedChartsWicketAppTearDownFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"WickedChartsWicketToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"WickedChartsWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"WickedChartsWicketToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"WickedChartsWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"WickedChartsWicketToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"WickedChartsWicketToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"WickedChartsWicketToDoItem\"");
        }
    }


    @javax.inject.Inject
    private org.apache.isis.applib.services.jdosupport.IsisJdoSupport isisJdoSupport;

}
