package org.incode.module.classification.fixture.scripts.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ClassificationDemoAppTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from \"incodeClassificationDemo\".\"ClassificationForOtherObject\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassificationDemo\".\"OtherObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeClassificationDemo\".\"ClassificationForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassificationDemo\".\"DemoObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Classification\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Applicability\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Category\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
