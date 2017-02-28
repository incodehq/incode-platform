package org.incode.module.communications.demo.module.dom.impl.commchannel;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner_newChannelContributions;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class DemoCommunicationChannelOwner_newChannelContributions extends
        CommunicationChannelOwner_newChannelContributions {

    public DemoCommunicationChannelOwner_newChannelContributions() {
        super(DemoCommunicationChannelOwner_newChannelContributions.class);
    }

}
