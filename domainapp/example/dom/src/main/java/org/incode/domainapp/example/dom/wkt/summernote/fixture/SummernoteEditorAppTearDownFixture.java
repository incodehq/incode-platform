package org.incode.domainapp.example.dom.wkt.summernote.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SummernoteEditorAppTearDownFixture extends FixtureScript {

    private final String ownedBy;

    public SummernoteEditorAppTearDownFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        if(ownedBy != null) {
            isisJdoSupport.executeUpdate("delete from \"exampleWktSummernote\".\"SummernoteEditorToDoItemDependencies\" where \"dependingId\" IN (select \"id\" from \"exampleWktSummernote\".\"SummernoteEditorToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"exampleWktSummernote\".\"SummernoteEditorToDoItemDependencies\" where \"dependentId\" IN (select \"id\" from \"exampleWktSummernote\".\"SummernoteEditorToDoItem\" where \"ownedBy\" = '" + ownedBy + "') ");
            isisJdoSupport.executeUpdate("delete from \"exampleWktSummernote\".\"SummernoteEditorToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");
        } else {
            isisJdoSupport.executeUpdate("delete from \"exampleWktSummernote\".\"SummernoteEditorToDoItemDependencies\"");
            isisJdoSupport.executeUpdate("delete from \"exampleWktSummernote\".\"SummernoteEditorWicketToDoItem\"");
        }
    }


    @javax.inject.Inject
    private org.apache.isis.applib.services.jdosupport.IsisJdoSupport isisJdoSupport;

}
