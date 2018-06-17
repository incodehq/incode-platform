package org.incode.example.communications.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.example.communications.dom.impl.commchannel.CommunicationChannel;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwnerLink;
import org.incode.example.communications.dom.impl.comms.CommChannelRole;
import org.incode.example.communications.dom.impl.comms.Communication;
import org.incode.example.communications.dom.impl.paperclips.PaperclipForCommunication;

public class CommunicationModule_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {

        deleteFrom(CommChannelRole.class);
        deleteFrom(PaperclipForCommunication.class);
        deleteFrom(Communication.class);

        deleteFrom(CommunicationChannelOwnerLink.class);
        deleteFrom(CommunicationChannel.class);
    }


}
