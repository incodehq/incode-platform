package org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

@XmlRootElement(name = "module")
public class FixturesModuleLibStringInterpolatorSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new DemoReminder_tearDown();
    }

}
