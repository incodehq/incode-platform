package org.incode.examples.commchannel.demo.shared.order;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.order.fixture.DemoOrderAndOrderLine_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedOrderSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoOrderAndOrderLine_tearDown();
    }
}
