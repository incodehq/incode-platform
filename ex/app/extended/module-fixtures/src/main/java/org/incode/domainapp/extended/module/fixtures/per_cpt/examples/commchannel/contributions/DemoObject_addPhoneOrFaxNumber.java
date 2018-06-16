package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;

@Mixin
public class DemoObject_addPhoneOrFaxNumber
        extends T_addPhoneOrFaxNumber<DemoObject> {
    public DemoObject_addPhoneOrFaxNumber(final DemoObject owner) {
        super(owner);
    }
}
