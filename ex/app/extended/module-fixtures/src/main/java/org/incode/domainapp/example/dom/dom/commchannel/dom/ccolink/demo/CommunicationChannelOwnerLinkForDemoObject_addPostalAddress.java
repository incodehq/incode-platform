package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_addPostalAddress
        extends T_addPostalAddress<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_addPostalAddress(final DemoObject owner) {
        super(owner);
    }
}
