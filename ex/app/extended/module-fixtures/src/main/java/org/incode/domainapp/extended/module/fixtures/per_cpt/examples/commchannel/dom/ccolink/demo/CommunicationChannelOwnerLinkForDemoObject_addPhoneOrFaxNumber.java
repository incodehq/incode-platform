package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.dom.ccolink.demo;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;

@Mixin
public class CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber
        extends T_addPhoneOrFaxNumber<DemoObject> {
    public CommunicationChannelOwnerLinkForDemoObject_addPhoneOrFaxNumber(final DemoObject owner) {
        super(owner);
    }
}
