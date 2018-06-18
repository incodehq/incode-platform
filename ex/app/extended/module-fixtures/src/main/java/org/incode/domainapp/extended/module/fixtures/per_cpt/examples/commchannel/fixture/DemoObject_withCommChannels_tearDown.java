package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.fixture.teardown.CommChannelModule_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.fixture.DemoObject_tearDown;

public class DemoObject_withCommChannels_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new CommChannelModule_tearDown());
        executionContext.executeChild(this, new DemoObject_tearDown());
    }

}
