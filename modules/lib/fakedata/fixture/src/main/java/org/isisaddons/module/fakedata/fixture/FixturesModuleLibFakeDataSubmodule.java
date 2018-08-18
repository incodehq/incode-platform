package org.isisaddons.module.fakedata.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

@XmlRootElement(name = "module")
public class FixturesModuleLibFakeDataSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithAll_tearDown();
    }

}
