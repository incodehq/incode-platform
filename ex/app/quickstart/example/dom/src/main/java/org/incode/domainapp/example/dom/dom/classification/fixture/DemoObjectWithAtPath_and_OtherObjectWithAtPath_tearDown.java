package org.incode.domainapp.example.dom.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAtPath_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObjectWithAtPath_tearDown;

public class DemoObjectWithAtPath_and_OtherObjectWithAtPath_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new ClassificationModule_tearDown());

        executionContext.executeChild(this, new DemoObjectWithAtPath_tearDown());
        executionContext.executeChild(this, new OtherObjectWithAtPath_tearDown());

    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
