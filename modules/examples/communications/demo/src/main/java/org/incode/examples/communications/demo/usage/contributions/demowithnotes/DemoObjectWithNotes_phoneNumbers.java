package org.incode.examples.communications.demo.usage.contributions.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.communications.demo.shared.demowithnotes.dom.DemoObjectWithNotes;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_phoneNumberTitles;

@Mixin(method = "prop")
public class DemoObjectWithNotes_phoneNumbers extends
        CommunicationChannelOwner_phoneNumberTitles {

    public DemoObjectWithNotes_phoneNumbers(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }

}
