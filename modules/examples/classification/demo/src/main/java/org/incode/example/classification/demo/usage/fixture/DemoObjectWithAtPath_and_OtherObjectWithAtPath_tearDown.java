package org.incode.example.classification.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.example.classification.demo.shared.demowithatpath.fixture.DemoObjectWithAtPath_tearDown;
import org.incode.example.classification.demo.shared.otherwithatpath.fixture.OtherObjectWithAtPath_tearDown;

public class DemoObjectWithAtPath_and_OtherObjectWithAtPath_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new Classifications_tearDown());

        executionContext.executeChild(this, new DemoObjectWithAtPath_tearDown());
        executionContext.executeChild(this, new OtherObjectWithAtPath_tearDown());

    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
