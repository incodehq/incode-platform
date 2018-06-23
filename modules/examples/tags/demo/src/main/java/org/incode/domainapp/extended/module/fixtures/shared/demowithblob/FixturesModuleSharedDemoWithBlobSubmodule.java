package org.incode.domainapp.extended.module.fixtures.shared.demowithblob;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.demowithblob.fixture.DemoObjectWithBlob_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithBlobSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithBlob_tearDown();
     }
}
