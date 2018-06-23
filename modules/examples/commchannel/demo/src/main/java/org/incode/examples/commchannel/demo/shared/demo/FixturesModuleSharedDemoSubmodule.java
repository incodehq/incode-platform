package org.incode.examples.commchannel.demo.shared.demo;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.demo.fixture.DemoObject_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObject_tearDown();

    }
}
