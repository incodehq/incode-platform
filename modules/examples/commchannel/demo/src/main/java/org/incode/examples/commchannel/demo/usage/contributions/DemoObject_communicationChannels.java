package org.incode.examples.commchannel.demo.usage.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.channel.T_communicationChannels;

@Mixin
public class DemoObject_communicationChannels
        extends T_communicationChannels<DemoObject> {
    public DemoObject_communicationChannels(final DemoObject owner) {
        super(owner);
    }
}
