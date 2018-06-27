package org.incode.example.commchannel.demo.usage.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;

@Mixin
public class CommChannelCustomer_addPhoneOrFaxNumber
        extends T_addPhoneOrFaxNumber<CommChannelCustomer> {
    public CommChannelCustomer_addPhoneOrFaxNumber(final CommChannelCustomer owner) {
        super(owner);
    }
}
