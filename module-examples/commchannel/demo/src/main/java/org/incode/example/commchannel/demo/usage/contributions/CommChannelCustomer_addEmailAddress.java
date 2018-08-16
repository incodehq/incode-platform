package org.incode.example.commchannel.demo.usage.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.dom.impl.emailaddress.T_addEmailAddress;

@Mixin
public class CommChannelCustomer_addEmailAddress
        extends T_addEmailAddress<CommChannelCustomer> {
    public CommChannelCustomer_addEmailAddress(final CommChannelCustomer owner) {
        super(owner);
    }
}
