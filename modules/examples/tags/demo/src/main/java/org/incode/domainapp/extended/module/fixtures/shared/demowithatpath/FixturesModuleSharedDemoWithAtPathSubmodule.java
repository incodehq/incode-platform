package org.incode.examples.commchannel.demo.shared.demowithatpath;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.demowithatpath.fixture.DemoObjectWithAtPath_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithAtPathSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithAtPath_tearDown();
    }
}
