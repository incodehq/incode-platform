package org.incode.example.commchannel.demo.usage.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.commchannel.demo.usage.dom.CommunicationChannelOwnerLinkForCommChannelCustomer;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

public class CommChannelModule_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        deleteFrom(CommunicationChannelOwnerLinkForCommChannelCustomer.class);
        deleteFrom(CommunicationChannelOwnerLink.class);
        deleteFrom(CommunicationChannel.class);
    }


}
