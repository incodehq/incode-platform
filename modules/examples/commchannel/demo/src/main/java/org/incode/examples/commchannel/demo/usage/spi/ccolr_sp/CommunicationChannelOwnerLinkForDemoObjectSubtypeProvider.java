package org.incode.examples.commchannel.demo.usage.spi.ccolr_sp;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.examples.commchannel.demo.usage.dom.CommunicationChannelOwnerLinkForDemoObject;
import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider extends CommunicationChannelOwnerLinkRepository.SubtypeProviderAbstract {
    public CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider() {
        super(DemoObject.class, CommunicationChannelOwnerLinkForDemoObject.class);
    }
}
