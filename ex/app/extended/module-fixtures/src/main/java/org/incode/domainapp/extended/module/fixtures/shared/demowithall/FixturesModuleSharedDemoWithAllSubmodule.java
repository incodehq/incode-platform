package org.incode.domainapp.extended.module.fixtures.shared.demowithall;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.demowithall.fixture.DemoObjectWithAll_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithAllSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithAll_tearDown();
    }
}
