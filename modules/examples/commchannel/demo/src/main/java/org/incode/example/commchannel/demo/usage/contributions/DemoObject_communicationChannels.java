package org.incode.example.commchannel.demo.usage.contributions;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.example.commchannel.demo.shared.dom.DemoObject;
import org.incode.example.commchannel.dom.impl.channel.T_communicationChannels;

@Mixin
public class DemoObject_communicationChannels
        extends T_communicationChannels<DemoObject> {
    public DemoObject_communicationChannels(final DemoObject owner) {
        super(owner);
    }
}
