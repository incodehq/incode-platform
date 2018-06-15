package org.incode.domainapp.module.fixtures.per_cpt.examples.communications.dom.demowithnotes;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotes;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;

@Mixin(method = "prop")
public class DemoObjectWithNotes_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public DemoObjectWithNotes_emailAddress(final DemoObjectWithNotes demoCustomer) {
        super(demoCustomer, " | ");
    }


}
