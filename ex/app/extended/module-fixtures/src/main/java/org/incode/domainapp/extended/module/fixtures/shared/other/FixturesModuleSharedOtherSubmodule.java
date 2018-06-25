package org.incode.domainapp.extended.module.fixtures.shared.other;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.other.fixture.OtherObject_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleSharedOtherSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new OtherObject_tearDown();
    }
}
