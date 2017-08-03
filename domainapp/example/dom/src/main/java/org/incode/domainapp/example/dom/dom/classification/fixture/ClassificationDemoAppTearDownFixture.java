package org.incode.domainapp.example.dom.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ClassificationDemoAppTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForOtherObject\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"OtherObject\"");

        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"DemoObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Classification\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Applicability\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Category\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
