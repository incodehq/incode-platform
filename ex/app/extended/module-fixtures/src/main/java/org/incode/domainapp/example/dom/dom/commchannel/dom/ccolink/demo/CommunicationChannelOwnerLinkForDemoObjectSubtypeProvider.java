package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider extends CommunicationChannelOwnerLinkRepository.SubtypeProviderAbstract {
    public CommunicationChannelOwnerLinkForDemoObjectSubtypeProvider() {
        super(DemoObject.class, CommunicationChannelOwnerLinkForDemoObject.class);
    }
}
