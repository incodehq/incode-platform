package org.incode.domainapp.example.dom.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAtPathTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObjectWithAtPathTearDown;

public class ClassificationDemoAppTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForOtherObjectWithAtPath\"");

        executionContext.executeChild(this, new OtherObjectWithAtPathTearDown());

        isisJdoSupport.executeUpdate("delete from \"exampleDomClassification\".\"ClassificationForDemoObjectWithAtPath\"");

        executionContext.executeChild(this, new DemoObjectWithAtPathTearDown());

        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Classification\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Applicability\"");
        isisJdoSupport.executeUpdate("delete from \"incodeClassification\".\"Category\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
