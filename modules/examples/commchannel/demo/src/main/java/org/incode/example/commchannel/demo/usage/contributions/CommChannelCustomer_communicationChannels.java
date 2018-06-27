package org.incode.example.commchannel.demo.usage.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.dom.impl.channel.T_communicationChannels;

@Mixin
public class CommChannelCustomer_communicationChannels
        extends T_communicationChannels<CommChannelCustomer> {
    public CommChannelCustomer_communicationChannels(final CommChannelCustomer owner) {
        super(owner);
    }
}
