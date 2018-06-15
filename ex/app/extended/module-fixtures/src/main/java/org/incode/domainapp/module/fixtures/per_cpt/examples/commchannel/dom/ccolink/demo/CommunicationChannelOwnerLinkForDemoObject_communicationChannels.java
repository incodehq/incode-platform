package org.incode.domainapp.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.channel.T_communicationChannels;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_communicationChannels
        extends T_communicationChannels<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_communicationChannels(final DemoObject owner) {
        super(owner);
    }
}
