package org.incode.example.communications.dom.impl;

import org.incode.example.communications.dom.impl.commchannel.CommunicationChannel;

@javax.jdo.annotations.Discriminator("org.incode.example.communications.dom.impl.CommunicationChannelForTesting")
public class CommunicationChannelForTesting extends CommunicationChannel {

    public String getName() {
        return null;
    }

}
