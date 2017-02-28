package org.incode.module.communications.demo.module.dom.impl.commchannel;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner_emailAddressTitles;

@Mixin
public class DemoCommunicationChannelOwner_emailAddress extends
        CommunicationChannelOwner_emailAddressTitles {

    public DemoCommunicationChannelOwner_emailAddress(final CommChannelDemoObject demoObject) {
        super(demoObject, " | ");
    }


}
