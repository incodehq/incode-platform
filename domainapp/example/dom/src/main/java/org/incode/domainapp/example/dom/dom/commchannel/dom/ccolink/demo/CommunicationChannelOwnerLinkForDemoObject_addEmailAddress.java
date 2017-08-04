package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_addEmailAddress
        extends T_addEmailAddress<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_addEmailAddress(final DemoObject owner) {
        super(owner);
    }
}
