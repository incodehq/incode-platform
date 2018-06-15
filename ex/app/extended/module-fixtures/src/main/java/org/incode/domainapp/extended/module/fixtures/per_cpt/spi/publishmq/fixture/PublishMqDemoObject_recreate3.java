package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.fixture.teardown.PublishMqDemoObject_tearDown;

public class PublishMqDemoObject_recreate3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

	    executionContext.executeChild(this, new PublishMqDemoObject_tearDown());
	    executionContext.executeChild(this, new PublishMqDemoObject_create3());
    }


}
