package org.incode.example.commchannel.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.commchannel.demo.shared.fixture.CommChannelCustomer_tearDown;
import org.incode.example.commchannel.demo.usage.fixture.teardown.CommChannelModule_tearDown;

public class CommChannelCustomer_withCommChannels_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new CommChannelModule_tearDown());
        executionContext.executeChild(this, new CommChannelCustomer_tearDown());
    }

}
