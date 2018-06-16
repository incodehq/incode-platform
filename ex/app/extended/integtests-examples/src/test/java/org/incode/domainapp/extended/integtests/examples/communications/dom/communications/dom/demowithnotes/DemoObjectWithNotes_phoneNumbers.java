package org.incode.domainapp.extended.integtests.examples.communications.dom.communications.dom.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_phoneNumberTitles;
import org.incode.domainapp.extended.integtests.examples.communications.demo.dom.demowithnotes.DemoObjectWithNotes;

@Mixin(method = "prop")
public class DemoObjectWithNotes_phoneNumbers extends
        CommunicationChannelOwner_phoneNumberTitles {

    public DemoObjectWithNotes_phoneNumbers(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }

}
