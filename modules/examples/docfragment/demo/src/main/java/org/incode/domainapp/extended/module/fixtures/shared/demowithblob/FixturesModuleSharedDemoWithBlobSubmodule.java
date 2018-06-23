package org.incode.examples.commchannel.demo.shared.demowithblob;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.demowithblob.fixture.DemoObjectWithBlob_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithBlobSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithBlob_tearDown();
     }
}
