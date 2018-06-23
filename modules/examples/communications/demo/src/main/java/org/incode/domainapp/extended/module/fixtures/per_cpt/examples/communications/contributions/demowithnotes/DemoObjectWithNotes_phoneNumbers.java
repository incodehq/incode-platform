package org.incode.example.alias.demo.examples.communications.contributions.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_phoneNumberTitles;
import org.incode.examples.commchannel.demo.shared.demowithnotes.dom.DemoObjectWithNotes;

@Mixin(method = "prop")
public class DemoObjectWithNotes_phoneNumbers extends
        CommunicationChannelOwner_phoneNumberTitles {

    public DemoObjectWithNotes_phoneNumbers(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }

}
