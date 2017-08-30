package org.incode.domainapp.example.dom.dom.communications.dom.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner_phoneNumberTitles;

@Mixin(method = "prop")
public class DemoObjectWithNotes_phoneNumbers extends
        CommunicationChannelOwner_phoneNumberTitles {

    public DemoObjectWithNotes_phoneNumbers(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }

}
