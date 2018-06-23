package org.incode.examples.commchannel.demo.shared.otherwithatpath;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.otherwithatpath.fixture.OtherObjectWithAtPath_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedOtherWithAtPathSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new OtherObjectWithAtPath_tearDown();
    }
}
