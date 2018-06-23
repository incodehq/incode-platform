package org.incode.examples.commchannel.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.examples.commchannel.demo.usage.fixture.teardown.CommChannelModule_tearDown;
import org.incode.examples.commchannel.demo.shared.demo.fixture.DemoObject_tearDown;

public class DemoObject_withCommChannels_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new CommChannelModule_tearDown());
        executionContext.executeChild(this, new DemoObject_tearDown());
    }

}
