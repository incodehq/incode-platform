package org.incode.example.alias.demo.examples.communications.contributions.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotes;

@Mixin(method = "prop")
public class DemoObjectWithNotes_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public DemoObjectWithNotes_emailAddress(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }


}
