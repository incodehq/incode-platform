package org.incode.domainapp.extended.module.fixtures.shared.demowithatpath;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.demowithatpath.fixture.DemoObjectWithAtPath_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithAtPathSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithAtPath_tearDown();
    }
}
