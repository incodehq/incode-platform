package org.incode.example.commchannel.demo.usage.spi.ccolr_sp;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.commchannel.demo.shared.dom.DemoObject;
import org.incode.example.commchannel.demo.usage.dom.CommunicationChannelOwnerLinkForDemoObject;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider extends CommunicationChannelOwnerLinkRepository.SubtypeProviderAbstract {
    public CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider() {
        super(DemoObject.class, CommunicationChannelOwnerLinkForDemoObject.class);
    }
}
