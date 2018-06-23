package org.incode.example.alias.demo.examples.commchannel.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.alias.demo.shared.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.emailaddress.T_addEmailAddress;

@Mixin
public class DemoObject_addEmailAddress
        extends T_addEmailAddress<DemoObject> {
    public DemoObject_addEmailAddress(final DemoObject owner) {
        super(owner);
    }
}
