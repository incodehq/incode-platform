package org.incode.module.communications.demo.module.dom.impl.customers;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;

@Mixin
public class DemoCustomer_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public DemoCustomer_emailAddress(final DemoCustomer demoCustomer) {
        super(demoCustomer, " | ");
    }


}
