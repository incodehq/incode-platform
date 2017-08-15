package org.incode.domainapp.example.dom.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;

public class DemoObjectWithAtPath_and_OtherObjectWithAtPath_withClassifications_withCategories_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // classifications
        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForOtherObjectWithAtPath\"");

        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForDemoObjectWithAtPath\"");


        // classification refdata
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Classification\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Applicability\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Category\"");

        // demo objects
        executionContext.executeChild(this, new DemoModuleTearDown());

    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
