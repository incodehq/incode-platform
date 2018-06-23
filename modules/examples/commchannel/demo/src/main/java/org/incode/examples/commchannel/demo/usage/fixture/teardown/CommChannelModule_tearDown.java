package org.incode.examples.commchannel.demo.usage.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.commchannel.demo.usage.dom.CommunicationChannelOwnerLinkForDemoObject;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

public class CommChannelModule_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        deleteFrom(CommunicationChannelOwnerLinkForDemoObject.class);
        deleteFrom(CommunicationChannelOwnerLink.class);
        deleteFrom(CommunicationChannel.class);
    }


}
