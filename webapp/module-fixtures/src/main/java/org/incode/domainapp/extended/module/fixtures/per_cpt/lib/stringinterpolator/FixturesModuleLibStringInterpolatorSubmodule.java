package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.fixture.DemoReminder_tearDown;

@XmlRootElement(name = "module")
public class FixturesModuleLibStringInterpolatorSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoReminder_tearDown();
    }

}
