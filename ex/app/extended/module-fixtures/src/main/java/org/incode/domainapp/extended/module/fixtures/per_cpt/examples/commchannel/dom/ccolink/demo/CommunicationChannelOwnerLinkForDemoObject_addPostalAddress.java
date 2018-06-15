package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_addPostalAddress
        extends T_addPostalAddress<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_addPostalAddress(final DemoObject owner) {
        super(owner);
    }
}
