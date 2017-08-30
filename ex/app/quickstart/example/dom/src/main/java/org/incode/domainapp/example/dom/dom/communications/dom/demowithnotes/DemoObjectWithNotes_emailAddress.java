package org.incode.domainapp.example.dom.dom.communications.dom.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;

@Mixin(method = "prop")
public class DemoObjectWithNotes_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public DemoObjectWithNotes_emailAddress(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }


}
