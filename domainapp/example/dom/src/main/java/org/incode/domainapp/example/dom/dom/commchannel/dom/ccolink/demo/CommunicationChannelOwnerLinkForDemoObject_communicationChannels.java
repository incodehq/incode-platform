package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.commchannel.dom.impl.channel.T_communicationChannels;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_communicationChannels
        extends T_communicationChannels<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_communicationChannels(final DemoObject owner) {
        super(owner);
    }
}
