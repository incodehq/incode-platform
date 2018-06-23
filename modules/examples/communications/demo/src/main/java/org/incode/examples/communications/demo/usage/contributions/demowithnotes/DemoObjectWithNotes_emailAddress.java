package org.incode.examples.communications.demo.usage.contributions.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.communications.demo.shared.demowithnotes.dom.DemoObjectWithNotes;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;

@Mixin(method = "prop")
public class DemoObjectWithNotes_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public DemoObjectWithNotes_emailAddress(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }


}
