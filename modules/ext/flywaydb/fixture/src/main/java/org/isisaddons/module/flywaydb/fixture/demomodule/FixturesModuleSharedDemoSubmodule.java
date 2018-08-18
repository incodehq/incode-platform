package org.isisaddons.module.flywaydb.fixture.demomodule;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts.DemoObject_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObject_tearDown();

    }
}
