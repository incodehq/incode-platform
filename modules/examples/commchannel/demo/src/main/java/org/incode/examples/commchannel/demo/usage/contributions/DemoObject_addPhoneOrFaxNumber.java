package org.incode.examples.commchannel.demo.usage.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;

@Mixin
public class DemoObject_addPhoneOrFaxNumber
        extends T_addPhoneOrFaxNumber<DemoObject> {
    public DemoObject_addPhoneOrFaxNumber(final DemoObject owner) {
        super(owner);
    }
}
