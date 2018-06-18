package org.incode.example.commchannel.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;

public class CommChannelModule_teardown extends TeardownFixtureAbstract2 {
    @Override protected void execute(final ExecutionContext executionContext) {
        deleteFrom(CommunicationChannelOwnerLink.class);
//        deleteFrom(PostalAddress.class);
//        deleteFrom(EmailAddress.class);
//        deleteFrom(PhoneOrFaxNumber.class);
        deleteFrom(CommunicationChannel.class);
    }
}
