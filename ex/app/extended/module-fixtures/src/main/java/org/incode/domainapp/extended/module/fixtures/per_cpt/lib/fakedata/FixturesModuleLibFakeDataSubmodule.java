package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.fixture.DemoObjectWithAll_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleLibFakeDataSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoObjectWithAll_tearDown();
    }

}
