package org.incode.example.communications.demo.usage.contributions.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsCustomer;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_phoneNumberTitles;

@Mixin(method = "prop")
public class CommsCustomer_phoneNumbers extends
        CommunicationChannelOwner_phoneNumberTitles {

    public CommsCustomer_phoneNumbers(final CommsCustomer demoCustomer) {
        super(demoCustomer, " | ");
    }

}
