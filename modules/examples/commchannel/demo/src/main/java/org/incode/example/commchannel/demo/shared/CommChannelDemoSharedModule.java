package org.incode.example.commchannel.demo.shared;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.commchannel.demo.shared.fixture.CommChannelCustomer_tearDown;

@XmlRootElement(name = "module")
public class CommChannelDemoSharedModule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new CommChannelCustomer_tearDown();

    }
}
