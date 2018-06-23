package org.incode.examples.commchannel.demo.shared.demowithall;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.demowithall.fixture.DemoObjectWithAll_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithAllSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithAll_tearDown();
    }
}
