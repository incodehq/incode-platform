package org.incode.domainapp.example.dom.dom.commchannel.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoObject_withCommChannels_recreate3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoObject_withCommChannels_tearDown());
        executionContext.executeChild(this, new DemoObject_withCommChannels_create3());

    }

}
