package org.isisaddons.module.fakedata.fixture;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.fixture.demoapp.demomodule.fixturescripts.DemoObjectWithAll_tearDown;

@XmlRootElement(name = "module")
public class FakeDataFixturesModule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithAll_tearDown();
    }

}
