package org.isisaddons.module.tags.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class ExampleTaggableEntitiesTearDownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"ExampleTaggableEntity\"");
        isisJdoSupport.executeUpdate("delete from \"isistags\".\"Tag\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
