package org.incode.example.commchannel.demo.usage.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;

@Mixin
public class CommChannelCustomer_addPostalAddress
        extends T_addPostalAddress<CommChannelCustomer> {
    public CommChannelCustomer_addPostalAddress(final CommChannelCustomer owner) {
        super(owner);
    }
}
