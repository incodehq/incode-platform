package org.incode.domainapp.example.dom.dom.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber
        extends T_addPhoneOrFaxNumber<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber(final DemoObject owner) {
        super(owner);
    }
}
