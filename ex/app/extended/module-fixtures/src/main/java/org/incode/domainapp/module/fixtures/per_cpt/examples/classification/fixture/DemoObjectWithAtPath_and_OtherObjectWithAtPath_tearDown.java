package org.incode.domainapp.module.fixtures.per_cpt.examples.classification.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.module.fixtures.shared.demowithatpath.fixture.DemoObjectWithAtPath_tearDown;
import org.incode.domainapp.module.fixtures.shared.otherwithatpath.fixture.OtherObjectWithAtPath_tearDown;

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
