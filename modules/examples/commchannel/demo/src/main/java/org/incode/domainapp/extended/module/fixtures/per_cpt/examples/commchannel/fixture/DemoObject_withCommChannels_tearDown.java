package org.incode.example.alias.demo.examples.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.example.alias.demo.examples.commchannel.fixture.teardown.CommChannelModule_tearDown;
import org.incode.example.alias.demo.shared.fixture.DemoObject_tearDown;

public class DemoObject_withCommChannels_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new CommChannelModule_tearDown());
        executionContext.executeChild(this, new DemoObject_tearDown());
    }

}
