package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_addPostalAddress
        extends T_addPostalAddress<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_addPostalAddress(final DemoObject owner) {
        super(owner);
    }
}
