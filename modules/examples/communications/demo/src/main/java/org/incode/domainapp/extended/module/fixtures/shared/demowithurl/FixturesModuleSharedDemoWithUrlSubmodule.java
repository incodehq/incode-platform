package org.incode.examples.commchannel.demo.shared.demowithurl;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.demowithurl.fixture.DemoObjectWithUrl_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithUrlSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithUrl_tearDown();
    }
}
