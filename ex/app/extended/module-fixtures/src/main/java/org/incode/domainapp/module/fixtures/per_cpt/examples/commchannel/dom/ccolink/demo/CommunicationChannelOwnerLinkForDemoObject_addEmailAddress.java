package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.emailaddress.T_addEmailAddress;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_addEmailAddress
        extends T_addEmailAddress<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_addEmailAddress(final DemoObject owner) {
        super(owner);
    }
}
