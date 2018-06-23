package org.incode.examples.commchannel.demo.shared.other;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.other.fixture.OtherObject_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedOtherSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new OtherObject_tearDown();
    }
}
