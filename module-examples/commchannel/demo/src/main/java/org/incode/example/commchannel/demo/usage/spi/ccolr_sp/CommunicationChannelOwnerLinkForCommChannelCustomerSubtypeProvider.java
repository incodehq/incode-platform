package org.incode.example.commchannel.demo.usage.spi.ccolr_sp;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.demo.usage.dom.CommunicationChannelOwnerLinkForCommChannelCustomer;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommunicationChannelOwnerLinkForCommChannelCustomerSubtypeProvider extends CommunicationChannelOwnerLinkRepository.SubtypeProviderAbstract {
    public CommunicationChannelOwnerLinkForCommChannelCustomerSubtypeProvider() {
        super(CommChannelCustomer.class, CommunicationChannelOwnerLinkForCommChannelCustomer.class);
    }
}
