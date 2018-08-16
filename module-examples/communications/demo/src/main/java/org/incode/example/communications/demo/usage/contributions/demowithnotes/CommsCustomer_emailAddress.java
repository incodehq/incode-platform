package org.incode.example.communications.demo.usage.contributions.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsCustomer;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;

@Mixin(method = "prop")
public class CommsCustomer_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public CommsCustomer_emailAddress(final CommsCustomer demoCustomer) {
        super(demoCustomer, " | ");
    }


}
