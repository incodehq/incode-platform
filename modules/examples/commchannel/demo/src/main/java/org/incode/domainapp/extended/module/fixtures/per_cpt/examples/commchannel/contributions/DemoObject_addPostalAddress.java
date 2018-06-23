package org.incode.example.alias.demo.examples.commchannel.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.postaladdress.T_addPostalAddress;

@Mixin
public class DemoObject_addPostalAddress
        extends T_addPostalAddress<DemoObject> {
    public DemoObject_addPostalAddress(final DemoObject owner) {
        super(owner);
    }
}
