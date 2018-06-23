package org.incode.example.alias.demo.examples.commchannel.spi.ccolr_sp;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.alias.demo.examples.commchannel.dom.CommunicationChannelOwnerLinkForDemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider extends CommunicationChannelOwnerLinkRepository.SubtypeProviderAbstract {
    public CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider() {
        super(DemoObject.class, CommunicationChannelOwnerLinkForDemoObject.class);
    }
}
